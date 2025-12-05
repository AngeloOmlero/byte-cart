package com.example.byte_cart.factory

import com.example.byte_cart.exception.ProductNotFoundException
import com.example.byte_cart.model.Cart
import com.example.byte_cart.model.CartItem
import com.example.byte_cart.service.ProductService
import org.springframework.stereotype.Component

@Component
class CartItemFactory(private val productService: ProductService) {

    fun createCartItem(cart: Cart, productId: Long, quantity: Int): CartItem {
        val product = productService.getProductById(productId) ?: throw ProductNotFoundException(productId)
        return CartItem(cart = cart, product = product, quantity = quantity, subtotal = product.price.multiply(quantity.toBigDecimal()))
    }
}
