package com.poyoring.users.dto

import io.swagger.v3.oas.annotations.media.Schema

@JvmRecord
@Schema(description = "회원 요청 DTO")
data class UserRequestDto(
    @Schema(description = "사용자의 이메일 주소", example = "user@example.com")
    val email: String,

    @Schema(description = "사용자의 닉네임", example = "닉네임")
    val nickname: String,

    @Schema(description = "사용자의 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    val profileUrl: String?
)