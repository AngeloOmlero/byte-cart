package com.example.byte_cart.factory

import com.example.byte_cart.model.*
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class OrderFactory {

    fun createOrder(user: User, cartItems: List<CartItem>): Order {
        val orderItems = cartItems.map { cartItem ->
            OrderItem(
                productName = cartItem.product.name,
                price = cartItem.product.price,
                quantity = cartItem.quantity,
                subtotal = cartItem.subtotal
            )
        }.toMutableList()

        val totalAmount = orderItems.sumOf { it.subtotal }

        val order = Order(
            user = user,
            totalAmount = totalAmount,
            createdAt = LocalDateTime.now(),
            status = OrderStatus.PENDING
        )

        orderItems.forEach { it.order = order }
        order.orderItems.addAll(orderItems)

        return order
    }
}
