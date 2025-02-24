package com.poyoring.photos.service

import com.poyoring.photos.entity.Photo
import com.poyoring.photos.repository.PhotoRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class PhotoService(private val photoRepository: PhotoRepository) {

    private val uploadDir = "/uploads" // 로컬 업로드 경로

    fun uploadPhoto(file: MultipartFile, description: String): Photo {
        val filename = file.originalFilename ?: throw IllegalArgumentException("파일명이 유효하지 않습니다.")
        val filePath = "$uploadDir/$filename"

        // 파일 저장
        try {
            Files.copy(file.inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            throw RuntimeException("파일 저장 중 오류 발생", e)
        }

        // 데이터베이스 저장
        val photo = Photo(
            filename = filename,
            fileUrl = filePath,
            description = description
        )

        return photoRepository.save(photo)
    }

    fun updatePhoto(id: Long, description: String): Photo {
        val photo = photoRepository.findById(id).orElseThrow { RuntimeException("사진을 찾을 수 없습니다.") }
        val updatedPhoto = photo.copy(description = description)
        return photoRepository.save(updatedPhoto)
    }

    fun deletePhoto(id: Long) {
        val photo = photoRepository.findById(id).orElseThrow { RuntimeException("사진을 찾을 수 없습니다.") }

        try {
            Files.deleteIfExists(Paths.get(photo.fileUrl)) // 파일 삭제
        } catch (e: IOException) {
            throw RuntimeException("파일 삭제 중 오류 발생", e)
        }

        photoRepository.delete(photo)
    }
}