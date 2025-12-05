package com.example.byte_cart.controller


import com.example.byte_cart.dto.AuthRequestDto
import com.example.byte_cart.dto.AuthResponseDto
import com.example.byte_cart.dto.CreateUserDto
import com.example.byte_cart.dto.UserDto
import com.example.byte_cart.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody createUserDto: CreateUserDto): UserDto {
        return authService.register(createUserDto)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody request : AuthRequestDto): AuthResponseDto {
        return authService.login(request)
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails): UserDto{
        val user = authService.getCurrentUser(userDetails.username)
        return UserDto(user.id, user.username)
    }
}