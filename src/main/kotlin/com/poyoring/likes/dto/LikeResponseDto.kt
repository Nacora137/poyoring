package com.poyoring.likes.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "좋아요 응답 DTO")
data class LikeResponseDto(
    @Schema(description = "피드 ID", example = "100")
    val feedId: Long,

    @Schema(description = "좋아요 개수", example = "25")
    val likeCount: Long
)