package com.example.byte_cart.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false, unique = true)
    var username: String = "",

    @JsonIgnore // Exclude password from JSON serialization
    @Column(nullable = false)
    var password: String? = "",

) {
    constructor(): this (0, "", "")
}
