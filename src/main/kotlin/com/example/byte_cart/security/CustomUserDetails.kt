package com.example.byte_cart.security



import com.example.byte_cart.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails(private val user: User) : UserDetails {

    val id: Long
        get() = user.id

    override fun getAuthorities() = emptyList<GrantedAuthority>()

    override fun getPassword(): String {
        return user.password!!
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}