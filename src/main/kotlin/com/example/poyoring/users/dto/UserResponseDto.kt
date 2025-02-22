package com.example.poyoring.users.dto

@JvmRecord
data class UserResponseDto(
    val id: Long?,
    val email: String,
    val nickname: String,
    val profileUrl: String?
)