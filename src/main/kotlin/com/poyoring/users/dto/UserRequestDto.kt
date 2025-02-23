package com.poyoring.users.dto

@JvmRecord
data class UserRequestDto(
    val email: String,
    val password: String,
    val nickname: String,
    val profileUrl: String
)