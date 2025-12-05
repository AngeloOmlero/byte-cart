package com.example.byte_cart.repository

import com.example.byte_cart.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>{
    fun findByUsername(username: String): User?
}