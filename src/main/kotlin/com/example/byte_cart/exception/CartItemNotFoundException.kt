package com.example.byte_cart.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cart item not found")
class CartItemNotFoundException(cartItemId: Long) : RuntimeException("Could not find cart item $cartItemId")
