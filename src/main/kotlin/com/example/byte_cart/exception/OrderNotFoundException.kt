package com.example.byte_cart.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order not found")
class OrderNotFoundException(orderId: Long) : RuntimeException("Could not find order $orderId")
