package com.example.byte_cart.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "cart_items")
class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product = Product(), // Initialize with a default Product for JPA no-arg constructor

    @JsonBackReference // This is the "child" side of the relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    var cart: Cart = Cart(), // Initialize with a default Cart for JPA no-arg constructor

    var quantity: Int = 0,

    var subtotal: BigDecimal = BigDecimal.ZERO
) {
    // JPA requires a no-arg constructor
    constructor() : this(product = Product(), cart = Cart())
}
