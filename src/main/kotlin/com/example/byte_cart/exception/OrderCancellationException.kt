package com.example.byte_cart.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Order cannot be cancelled")
class OrderCancellationException(message: String) : RuntimeException(message)
