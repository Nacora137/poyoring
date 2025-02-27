package com.poyoring.likes.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "좋아요한 사용자 DTO")
data class UserLikeDto(
    @Schema(description = "사용자 ID", example = "1")
    val userId: Long
)