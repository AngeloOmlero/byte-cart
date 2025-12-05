package com.example.byte_cart.dto

import java.math.BigDecimal

data class OrderItemDTO(
    val id: Long,
    val productName: String,
    val price: BigDecimal,
    val quantity: Int,
    val subtotal: BigDecimal
)
