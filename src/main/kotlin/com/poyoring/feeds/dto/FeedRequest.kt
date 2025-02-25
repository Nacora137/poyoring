package com.poyoring.feeds.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Feed 생성 요청 DTO")
data class FeedRequest(
    @Schema(description = "유저 ID", example = "1")
    val userId: Long,

    @Schema(description = "피드 내용", example = "오늘 찍은 사진 공유해요!")
    val content: String,

    @Schema(description = "연결할 사진 ID 리스트", example = "[1001, 1002, 1003]")
    val photoIds: List<Long>
)