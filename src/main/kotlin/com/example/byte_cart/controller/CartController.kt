package com.example.byte_cart.controller

import com.example.byte_cart.dto.CartItemRequest
import com.example.byte_cart.dto.UpdateCartItemRequestDto // Import the new DTO
import com.example.byte_cart.model.Cart
import com.example.byte_cart.model.Order
import com.example.byte_cart.service.AuthService
import com.example.byte_cart.service.CartService
import com.example.byte_cart.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartService: CartService,
    private val authService: AuthService,
    private val orderService: OrderService
) {

    private fun getCurrentUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication?.name ?: throw IllegalStateException("User not authenticated")
        return authService.getCurrentUser(username).id
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getCart(): Cart {
        return cartService.getCartByUserId(getCurrentUserId())
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    fun addProductToCart(@RequestBody cartItemRequest: CartItemRequest): Cart {
        return cartService.addProductToCart(getCurrentUserId(), cartItemRequest.productId, cartItemRequest.quantity)
    }

    @PutMapping("/update/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCartItemQuantity(
        @PathVariable itemId: Long,
        @RequestBody updateCartItemRequestDto: UpdateCartItemRequestDto // Use the new DTO
    ): Cart {
        return cartService.updateCartItemQuantity(getCurrentUserId(), itemId, updateCartItemRequestDto.quantity)
    }

    @DeleteMapping("/remove/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeCartItem(@PathVariable itemId: Long) {
        cartService.removeCartItem(getCurrentUserId(), itemId)
    }

    @PostMapping("/checkout/{cartItemId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun checkoutCart(@PathVariable cartItemId: Long): Order {
        return orderService.checkoutCart(getCurrentUserId(), cartItemId)
    }
}
