package com.example.byte_cart.dto

import java.math.BigDecimal

data class CartDTO(
    val id: Long,
    val userId: Long,
    val items: List<CartItemDTO>,
    val totalAmount: BigDecimal
)
