package com.example.byte_cart.service




import com.example.byte_cart.dto.AuthRequestDto
import com.example.byte_cart.dto.AuthResponseDto
import com.example.byte_cart.dto.CreateUserDto
import com.example.byte_cart.dto.UserDto
import com.example.byte_cart.model.User
import com.example.byte_cart.repository.UserRepository
import com.example.byte_cart.security.JWTUtil
import com.example.byte_cart.security.UserDetailsServiceImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.collections.distinctBy
import kotlin.collections.map
import kotlin.let
import kotlin.text.toLong

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JWTUtil,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsServiceImpl,
) {

    fun register(createUserDto: CreateUserDto): UserDto {
        userRepository.findByUsername(createUserDto.username)
            ?.let { throw kotlin.IllegalArgumentException("Username ${createUserDto.username} is already in use") }

        val createUser = User(
            username = createUserDto.username,
            password = passwordEncoder.encode(createUserDto.password)
        )

        val savedUser = userRepository.save(createUser)

        return UserDto(savedUser.id, savedUser.username)

    }

    fun getCurrentUser(username: String): UserDto {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")
        return UserDto(user.id, user.username)
    }


    fun login(request: AuthRequestDto): AuthResponseDto {
        return try {
            userDetailsService.loadUserByUsername(request.username)

            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.username, request.password)
            )

            SecurityContextHolder.getContext().authentication = authentication

            val userDetails = authentication.principal as UserDetails
            val token = jwtUtil.generateToken(userDetails)

            AuthResponseDto(token)

        } catch (ex: Exception) {
            when (ex) {
                is BadCredentialsException -> throw BadCredentialsException("Invalid username or password")
                is UsernameNotFoundException -> throw UsernameNotFoundException("Invalid username: ${request.username}")
                else -> throw kotlin.RuntimeException("Authentication failed: ${ex.message}")
            }

        }

    }

}