package com.poyoring.photos.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Photo 업로드 요청 DTO")
data class PhotoRequest(
    @Schema(description = "유저 ID", example = "1")
    val userId: Long,

    @Schema(description = "파일명", example = "profile.jpg")
    val filename: String?,

    @Schema(description = "파일 URL", example = "https://example.com/photos/profile.jpg")
    val fileUrl: String?
)
