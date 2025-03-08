package com.poyoring.security

import com.poyoring.users.entity.User
import com.poyoring.users.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found with email: $username")

        return org.springframework.security.core.userdetails.User(
            user.email, "", emptyList()
        )
    }
}