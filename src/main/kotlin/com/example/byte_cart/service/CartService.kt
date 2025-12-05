package com.example.byte_cart.service

import com.example.byte_cart.exception.CartItemNotFoundException
import com.example.byte_cart.factory.CartItemFactory
import com.example.byte_cart.model.Cart
import com.example.byte_cart.model.User
import com.example.byte_cart.repository.CartItemRepository
import com.example.byte_cart.repository.CartRepository
import com.example.byte_cart.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val userRepository: UserRepository,
    private val cartCalculationService: CartCalculationService,
    private val cartItemFactory: CartItemFactory
) {

    fun getCartByUserId(userId: Long): Cart {
        val user = userRepository.findById(userId).orElseThrow { RuntimeException("User not found") }
        return cartRepository.findByUser(user) ?: createCartForUser(user)
    }

    @Transactional
    fun addProductToCart(userId: Long, productId: Long, quantity: Int): Cart {
        val cart = getCartByUserId(userId)
        val existingCartItem = cart.items.find { it.product.id == productId }

        if (existingCartItem != null) {
            existingCartItem.quantity += quantity
        } else {
            val newCartItem = cartItemFactory.createCartItem(cart, productId, quantity)
            cart.items.add(newCartItem)
        }
        cartCalculationService.recalculateCartTotals(cart)
        return cartRepository.save(cart)
    }

    @Transactional
    fun updateCartItemQuantity(userId: Long, cartItemId: Long, quantity: Int): Cart { // Changed return type to Cart
        val cart = getCartByUserId(userId)
        val cartItem = cart.items.find { it.id == cartItemId } ?: throw CartItemNotFoundException(cartItemId)

        cartItem.quantity = quantity
        if (cartItem.quantity <= 0) {
            cart.items.remove(cartItem)
            cartItemRepository.delete(cartItem)
        }
        cartCalculationService.recalculateCartTotals(cart)
        return cartRepository.save(cart)
    }

    @Transactional
    fun removeCartItem(userId: Long, cartItemId: Long): Cart {
        val cart = getCartByUserId(userId)
        val cartItem = cart.items.find { it.id == cartItemId } ?: throw CartItemNotFoundException(cartItemId)

        cart.items.remove(cartItem)
        cartItemRepository.delete(cartItem)
        cartCalculationService.recalculateCartTotals(cart)
        return cartRepository.save(cart)
    }

    // New method to remove a cart item by its ID, used by OrderService
    @Transactional
    fun removeCartItemById(cartItemId: Long) {
        cartItemRepository.deleteById(cartItemId)
    }

    // Expose recalculateCartTotals for use by other services
    fun recalculateCartTotals(cart: Cart) {
        cartCalculationService.recalculateCartTotals(cart)
    }

    fun saveCart(cart: Cart): Cart {
        return cartRepository.save(cart)
    }

    private fun createCartForUser(user: User): Cart {
        val newCart = Cart(user = user)
        return cartRepository.save(newCart)
    }

}
