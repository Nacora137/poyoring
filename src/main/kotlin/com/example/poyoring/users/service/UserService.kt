package com.example.poyoring.users.service

import com.example.poyoring.users.dto.UserRequestDto
import com.example.poyoring.users.dto.UserResponseDto
import com.example.poyoring.users.entity.User
import com.example.poyoring.users.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(userRepository: UserRepository) {
    private val userRepository: UserRepository = userRepository

    fun createUser(requestDto: UserRequestDto): UserResponseDto {
        val user: User = User(null, requestDto.email, requestDto.password, requestDto.nickname, requestDto.profileUrl)
        val savedUser: User = userRepository.save(user)
        return UserResponseDto(savedUser.id, savedUser.email, savedUser.nickname, savedUser.profileUrl)
    }
}