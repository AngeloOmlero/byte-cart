package com.example.byte_cart.config

import com.example.byte_cart.model.Product
import com.example.byte_cart.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import org.slf4j.LoggerFactory

@Component
class DataInitializer(private val productRepository: ProductRepository) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(DataInitializer::class.java)

    override fun run(vararg args: String) {
        if (productRepository.count() == 0L) {
            logger.info("Initializing product data...")
            val products = listOf(
                Product(name = "Laptop", description = "High-performance laptop", price = BigDecimal("1499.99"), stock = 50, imageUrl = "/images/laptop.png"),
                Product(name = "Smartphone", description = "Latest model smartphone", price = BigDecimal("1999.00"), stock = 150, imageUrl = "/images/smarthphone.jpg"),
                Product(name = "Headphones", description = "Noise-cancelling headphones", price = BigDecimal("149.99"), stock = 300, imageUrl = "/images/headphones.jpg"),
                Product(name = "Keyboard", description = "Mechanical keyboard", price = BigDecimal("429.00"), stock = 200, imageUrl = "/images/keyboard.png"),
                Product(name = "Mouse", description = "Wireless gaming mouse", price = BigDecimal("179.99"), stock = 250, imageUrl = "/images/mouse.png"),
                Product(name = "Monitor", description = "27-inch 4K UHD monitor", price = BigDecimal("1099.00"), stock = 100, imageUrl = "/images/monitor.jpeg"),
                Product(name = "Webcam", description = "Full HD 1080p webcam", price = BigDecimal("199.99"), stock = 350, imageUrl = "/images/webcam.jpeg"),
                Product(name = "Microphone", description = "USB condenser microphone", price = BigDecimal("149.00"), stock = 180, imageUrl = "/images/microphone.jpg"),
                Product(name = "External SSD", description = "1TB portable SSD", price = BigDecimal("59.99"), stock = 120, imageUrl = "/images/ssd.jpeg"),
                Product(name = "Router", description = "Wi-Fi 6 Mesh Router", price = BigDecimal("89.00"), stock = 90, imageUrl = "/images/router.jpeg"),
                Product(name = "Smartwatch", description = "Fitness tracker with heart rate monitor", price = BigDecimal("729.00"), stock = 280, imageUrl = "/images/smartwatch.jpeg"),
                Product(name = "Tablet", description = "10.2-inch tablet with Retina display", price = BigDecimal("379.00"), stock = 130, imageUrl = "/images/tablet.jpeg"),
                Product(name = "Gaming Chair", description = "Ergonomic gaming chair with lumbar support", price = BigDecimal("199.99"), stock = 70, imageUrl = "/images/gaming_chair.jpeg"),
                Product(name = "Printer", description = "All-in-one wireless inkjet printer", price = BigDecimal("319.00"), stock = 160, imageUrl = "/images/printer.jpeg"),
                Product(name = "VR Headset", description = "Virtual reality headset with controllers", price = BigDecimal("399.00"), stock = 40, imageUrl = "/images/vr_headset.jpeg"),
                Product(name = "Drone", description = "Foldable drone with 4K camera", price = BigDecimal("699.00"), stock = 60, imageUrl = "/images/drone.png"),
                Product(name = "Action Camera", description = "Waterproof 4K action camera", price = BigDecimal("199.00"), stock = 220, imageUrl = "/images/action_camera.jpeg")
            )
            productRepository.saveAll(products)
            logger.info("Successfully initialized ${products.size} products.")
        } else {
            logger.info("Products already exist in the database. Skipping initialization.")
        }
    }
}
