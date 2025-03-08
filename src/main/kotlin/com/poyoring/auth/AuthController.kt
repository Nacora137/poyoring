package com.poyoring.auth

import com.poyoring.security.JwtProvider
import com.poyoring.users.entity.User
import com.poyoring.users.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val userDetailsService: UserDetailsService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        val userDetails = userDetailsService.loadUserByUsername(request.username)
        val token = jwtProvider.generateToken(userDetails)
        return ResponseEntity.ok(AuthResponse(token))
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<String> {
        if (userRepository.findByUsername(request.username) != null) {
            return ResponseEntity.badRequest().body("Username already exists")
        }
        val user = User(
            username = request.username
        )
        userRepository.save(user)
        return ResponseEntity.ok("User registered successfully")
    }
}

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String)
data class RegisterRequest(val username: String, val password: String)
