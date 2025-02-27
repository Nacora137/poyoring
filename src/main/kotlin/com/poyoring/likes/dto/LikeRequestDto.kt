package com.poyoring.likes.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "좋아요 요청 DTO")
data class LikeRequestDto(
    @Schema(description = "사용자 ID", example = "1")
    val userId: Long
)
