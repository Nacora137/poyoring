package com.poyoring.photos.service

import com.poyoring.photos.dto.PhotoRequest
import com.poyoring.photos.dto.PhotoResponse
import com.poyoring.photos.entity.Photo
import com.poyoring.photos.repository.PhotoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class PhotoService(private val photoRepository: PhotoRepository) {

    @Transactional
    fun uploadPhoto(request: PhotoRequest): PhotoResponse {
        val photo = photoRepository.save(
            Photo(
                userId = request.userId,
                filename = request.filename,
                fileUrl = request.fileUrl
            )
        )
        return toPhotoResponse(photo)
    }

    @Transactional(readOnly = true)
    fun getPhoto(id: Long): PhotoResponse {
        val photo = photoRepository.findById(id).orElseThrow { RuntimeException("Photo not found") }
        return toPhotoResponse(photo)
    }

    @Transactional(readOnly = true)
    fun getUserPhotos(userId: Long): List<PhotoResponse> {
        return photoRepository.findByUserId(userId).map { toPhotoResponse(it) }
    }

    @Transactional
    fun deletePhoto(id: Long) {
        if (!photoRepository.existsById(id)) throw RuntimeException("Photo not found")
        photoRepository.deleteById(id)
    }

    private fun toPhotoResponse(photo: Photo): PhotoResponse {
        return PhotoResponse(
            id = photo.id!!,
            userId = photo.userId,
            filename = photo.filename,
            fileUrl = photo.fileUrl,
            uploadedAt = photo.uploadedAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
    }
}