package com.example.byte_cart.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "carts")
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = User(), // Initialize with a default User for JPA no-arg constructor

    @JsonManagedReference // This is the "parent" side of the relationship
    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableList<CartItem> = mutableListOf(),

    var totalAmount: BigDecimal = BigDecimal.ZERO
) {
    // JPA requires a no-arg constructor
    constructor() : this(user = User())
}
