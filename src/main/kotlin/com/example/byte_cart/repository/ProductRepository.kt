package com.example.byte_cart.repository

import com.example.byte_cart.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
