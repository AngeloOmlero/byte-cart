package com.example.byte_cart.controller

import com.example.byte_cart.model.Order
import com.example.byte_cart.service.AuthService
import com.example.byte_cart.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService,
    private val authService: AuthService
) {

    private fun getCurrentUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication?.name ?: throw IllegalStateException("User not authenticated")
        return authService.getCurrentUser(username).id
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllOrders(): List<Order> {
        return orderService.getAllOrders(getCurrentUserId())
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getOrderById(@PathVariable id: Long): Order {
        return orderService.getOrderById(id)
    }

    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    fun cancelOrder(@PathVariable id: Long): Order {
        return orderService.cancelOrder(id)
    }
}
