package com.poyoring.photos.controller

import com.poyoring.photos.dto.PhotoDto
import com.poyoring.photos.entity.Photo
import com.poyoring.photos.service.PhotoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/photos")
@Tag(name = "사진 관리 API", description = "사진 업로드, 수정, 삭제 기능을 제공합니다.")
class PhotoController(private val photoService: PhotoService) {

    @PostMapping
    @Operation(summary = "사진 업로드", description = "이미지를 업로드하고 설명을 추가합니다.")
    fun uploadPhoto(
        @Parameter(description = "업로드할 이미지 파일", required = true) @RequestParam("file") file: MultipartFile,
        @Parameter(description = "사진 설명", required = false) @RequestParam("description") description: String
    ): ResponseEntity<PhotoDto> {
        val photo = photoService.uploadPhoto(file, description)
        return ResponseEntity.ok(PhotoDto.fromEntity(photo))
    }

    @PutMapping("/{id}")
    @Operation(summary = "사진 정보 수정", description = "사진 ID를 이용하여 설명을 수정합니다.")
    fun updatePhoto(
        @Parameter(description = "수정할 사진의 ID", required = true) @PathVariable id: Long,
        @Parameter(description = "새로운 사진 설명", required = true) @RequestParam("description") description: String
    ): ResponseEntity<PhotoDto> {
        val updatedPhoto = photoService.updatePhoto(id, description)
        return ResponseEntity.ok(PhotoDto.fromEntity(updatedPhoto))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "사진 삭제", description = "사진 ID를 이용하여 해당 사진을 삭제합니다.")
    fun deletePhoto(
        @Parameter(description = "삭제할 사진의 ID", required = true) @PathVariable id: Long
    ): ResponseEntity<Void> {
        photoService.deletePhoto(id)
        return ResponseEntity.noContent().build()
    }
}