package com.poyoring.photos.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Photo 응답 DTO")
data class PhotoResponse(
    @Schema(description = "사진 ID", example = "1001")
    val id: Long,

    @Schema(description = "유저 ID", example = "1")
    val userId: Long,

    @Schema(description = "파일명", example = "profile.jpg")
    val filename: String?,

    @Schema(description = "파일 URL", example = "https://example.com/photos/profile.jpg")
    val fileUrl: String?,

    @Schema(description = "업로드 시간", example = "2024-02-25T12:00:00")
    val uploadedAt: String
)