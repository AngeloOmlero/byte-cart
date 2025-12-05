package com.example.byte_cart.repository

import com.example.byte_cart.model.Cart
import com.example.byte_cart.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {
    fun findByUser(user: User): Cart?
}
