package com.example.byte_cart.repository

import com.example.byte_cart.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemRepository : JpaRepository<CartItem, Long>
