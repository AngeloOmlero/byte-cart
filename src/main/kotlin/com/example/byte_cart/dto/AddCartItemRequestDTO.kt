package com.example.byte_cart.dto

data class AddCartItemRequestDTO(
    val productId: Long,
    val quantity: Int
)
