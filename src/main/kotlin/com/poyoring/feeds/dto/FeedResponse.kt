package com.poyoring.feeds.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Feed 응답 DTO")
data class FeedResponse(
    @Schema(description = "피드 ID", example = "1")
    val id: Long,

    @Schema(description = "유저 ID", example = "1")
    val userId: Long,

    @Schema(description = "피드 내용", example = "오늘 찍은 사진 공유해요!")
    val content: String,

    @Schema(description = "연결된 사진 ID 목록", example = "[1001, 1002, 1003]")
    val photoIds: List<Long>
)