package com.example.byte_cart.controller

import com.example.byte_cart.exception.ProductNotFoundException
import com.example.byte_cart.model.Product
import com.example.byte_cart.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllProducts(): List<Product> = productService.getAllProducts()

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getProductById(@PathVariable id: Long): Product =
        productService.getProductById(id) ?: throw ProductNotFoundException(id)
}
