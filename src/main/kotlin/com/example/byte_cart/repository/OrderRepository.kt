package com.example.byte_cart.repository

import com.example.byte_cart.model.Order
import com.example.byte_cart.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUser(user: User): List<Order>
}
