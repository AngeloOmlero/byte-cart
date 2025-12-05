package com.example.byte_cart.service

import com.example.byte_cart.exception.OrderCancellationException
import com.example.byte_cart.exception.OrderNotFoundException
import com.example.byte_cart.factory.OrderFactory
import com.example.byte_cart.model.Order
import com.example.byte_cart.model.OrderStatus
import com.example.byte_cart.repository.OrderRepository
import com.example.byte_cart.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val cartService: CartService,
    private val orderFactory: OrderFactory,
    private val userRepository: UserRepository
) {

    @Transactional
    fun checkoutCart(userId: Long, cartItemId: Long): Order { // Changed to accept single cartItemId
        val user = userRepository.findById(userId).orElseThrow { RuntimeException("User not found") }
        val cart = cartService.getCartByUserId(userId)

        val selectedCartItem = cart.items.find { it.id == cartItemId }
            ?: throw IllegalArgumentException("Cart item with ID $cartItemId not found in your cart.")

        // Create a new order with just this single item
        val newOrder = orderFactory.createOrder(user, listOf(selectedCartItem))
        val savedOrder = orderRepository.save(newOrder)

        // Remove the checked-out item from the cart
        cart.items.remove(selectedCartItem)
        cartService.removeCartItemById(selectedCartItem.id)
        
        cartService.recalculateCartTotals(cart) // Recalculate cart total after removing the item
        cartService.saveCart(cart) // Save the updated cart

        return savedOrder
    }

    fun getAllOrders(userId: Long): List<Order> {
        val user = userRepository.findById(userId).orElseThrow { RuntimeException("User not found") }
        return orderRepository.findByUser(user)
    }

    fun getOrderById(orderId: Long): Order {
        return orderRepository.findById(orderId).orElseThrow { OrderNotFoundException(orderId) }
    }

    @Transactional
    fun cancelOrder(orderId: Long): Order {
        val order = getOrderById(orderId)

        if (order.status != OrderStatus.PENDING) {
            throw OrderCancellationException("Order ${order.id} cannot be cancelled as its status is ${order.status}")
        }

        val sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS)
        if (order.createdAt.isBefore(sevenDaysAgo)) {
            throw OrderCancellationException("Order ${order.id} cannot be cancelled as it is older than 7 days.")
        }

        order.status = OrderStatus.CANCELLED
        return orderRepository.save(order)
    }
}
