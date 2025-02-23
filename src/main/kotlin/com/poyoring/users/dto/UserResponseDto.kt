package com.poyoring.users.dto

import io.swagger.v3.oas.annotations.media.Schema

@JvmRecord
@Schema(description = "회원 요청 DTO")
data class UserResponseDto(

    @Schema(description = "회원 ID", example = "1")
    val id: Long?,

    @Schema(description = "회원 이메일", example = "user@example.com")
    val email: String,

    @Schema(description = "회원 닉네임", example = "닉네임")
    val nickname: String,

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    val profileUrl: String?

)