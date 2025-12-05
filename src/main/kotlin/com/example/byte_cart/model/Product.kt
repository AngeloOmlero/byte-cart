package com.example.byte_cart.model

import java.math.BigDecimal
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var price: BigDecimal = BigDecimal.ZERO,
    var stock: Int = 0,
    var imageUrl: String? = null
)
