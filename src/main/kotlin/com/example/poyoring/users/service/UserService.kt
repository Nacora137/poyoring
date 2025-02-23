package com.example.poyoring.users.service

import com.example.poyoring.users.dto.UserRequestDto
import com.example.poyoring.users.dto.UserResponseDto
import com.example.poyoring.users.entity.User
import com.example.poyoring.users.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService( private val userRepository: UserRepository) {

    @Transactional
    fun createUser(requestDto: UserRequestDto): UserResponseDto {
        val user: User = User(null, requestDto.email, requestDto.password, requestDto.nickname, requestDto.profileUrl)
        val savedUser: User = userRepository.save(user)
        return UserResponseDto(savedUser.id, savedUser.email, savedUser.nickname, savedUser.profileUrl)
    }

    @Transactional
    fun updateUser(id: Long, requestDto: UserRequestDto): UserResponseDto {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: $id") }

        user.updateProfile(requestDto.nickname, requestDto.profileUrl)

        return UserResponseDto(user.id, user.email, user.nickname, user.profileUrl)
    }

    @Transactional
    fun deleteUser(id: Long) {
        val user: User = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: $id") }

        userRepository.delete(user)
    }

}