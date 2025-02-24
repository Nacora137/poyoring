package com.poyoring.photos.dto

import com.poyoring.photos.entity.Photo

data class PhotoDto(
    val id: Long,
    val filename: String,
    val fileUrl: String,
    val description: String
) {
    companion object {
        fun fromEntity(photo: Photo) = PhotoDto(
            id = photo.id,
            filename = photo.filename,
            fileUrl = photo.fileUrl,
            description = photo.description
        )
    }
}