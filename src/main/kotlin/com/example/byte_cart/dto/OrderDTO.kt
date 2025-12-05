package com.example.byte_cart.dto

import com.example.byte_cart.model.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDTO(
    val id: Long,
    val userId: Long,
    val orderItems: List<OrderItemDTO>,
    val totalAmount: BigDecimal,
    val createdAt: LocalDateTime,
    val status: OrderStatus
)
