package com.example.byte_cart.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @JsonBackReference // This is the "child" side of the relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null, // This will be set when the Order is created

    var productName: String = "",
    var price: BigDecimal = BigDecimal.ZERO,
    var quantity: Int = 0,
    var subtotal: BigDecimal = BigDecimal.ZERO
) {
    // JPA requires a no-arg constructor
    constructor() : this(order = null)
}
