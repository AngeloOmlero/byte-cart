package com.example.byte_cart.service

import com.example.byte_cart.model.Cart
import com.example.byte_cart.model.CartItem
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CartCalculationService {

    fun recalculateCartTotals(cart: Cart) {
        var totalAmount = BigDecimal.ZERO
        for (item in cart.items) {
            item.subtotal = item.product.price.multiply(BigDecimal(item.quantity))
            totalAmount = totalAmount.add(item.subtotal)
        }
        cart.totalAmount = totalAmount
    }

    fun calculateCartItemSubtotal(item: CartItem): BigDecimal {
        return item.product.price.multiply(BigDecimal(item.quantity))
    }
}
