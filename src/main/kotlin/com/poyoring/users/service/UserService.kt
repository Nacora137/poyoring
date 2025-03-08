package com.poyoring.users.service

import com.poyoring.users.dto.UserRequestDto
import com.poyoring.users.dto.UserResponseDto
import com.poyoring.users.entity.User
import com.poyoring.users.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserById(id: Long): UserResponseDto {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: $id") }

        return UserResponseDto(user.id!!, user.email ?: "", user.nickname ?: "", user.profileUrl ?: "")
    }

    @Transactional
    fun createUser(requestDto: UserRequestDto): UserResponseDto {
        val user = User(
            id = null,
            email = requestDto.email,
            nickname = requestDto.nickname,
            profileUrl = requestDto.profileUrl
        )
        val savedUser = userRepository.save(user)
        return UserResponseDto(savedUser.id, savedUser.email ?: "", savedUser.nickname ?: "", savedUser.profileUrl ?: "")
    }

    @Transactional
    fun updateUser(id: Long, requestDto: UserRequestDto): UserResponseDto {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: $id") }

        user.nickname = requestDto.nickname ?: user.nickname
        user.profileUrl = requestDto.profileUrl ?: user.profileUrl

        return UserResponseDto(user.id, user.email ?: "", user.nickname ?: "", user.profileUrl ?: "")
    }

    @Transactional
    fun deleteUser(id: Long) {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: $id") }

        userRepository.delete(user)
    }
}