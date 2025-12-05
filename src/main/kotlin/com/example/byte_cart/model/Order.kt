package com.example.byte_cart.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders") // Renamed to avoid conflict with SQL 'ORDER' keyword
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = User(), // Initialize with a default User for JPA no-arg constructor

    @JsonManagedReference // This is the "parent" side of the relationship
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orderItems: MutableList<OrderItem> = mutableListOf(),

    var totalAmount: BigDecimal = BigDecimal.ZERO,

    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING // Default status
) {
    // JPA requires a no-arg constructor
    constructor() : this(user = User())
}
