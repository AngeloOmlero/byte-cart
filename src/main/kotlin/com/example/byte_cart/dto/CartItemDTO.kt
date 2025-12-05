package com.example.byte_cart.dto

import java.math.BigDecimal

data class CartItemDTO(
    val id: Long,
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val price: BigDecimal,
    val subtotal: BigDecimal
)
