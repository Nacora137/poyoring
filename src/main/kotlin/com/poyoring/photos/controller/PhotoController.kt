package com.poyoring.photos.controller

import com.poyoring.photos.dto.PhotoRequest
import com.poyoring.photos.dto.PhotoResponse
import com.poyoring.photos.service.PhotoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Photo API", description = "사진 관리 API")
@RestController
@RequestMapping("/api/photos")
class PhotoController(private val photoService: PhotoService) {

    @Operation(summary = "사진 업로드", description = "새로운 사진을 업로드합니다.")
    @PostMapping
    fun uploadPhoto(@RequestBody request: PhotoRequest): ResponseEntity<PhotoResponse> {
        return ResponseEntity.ok(photoService.uploadPhoto(request))
    }

    @Operation(summary = "사진 조회", description = "특정 사진 정보를 조회합니다.")
    @GetMapping("/{id}")
    fun getPhoto(@PathVariable id: Long): ResponseEntity<PhotoResponse> {
        return ResponseEntity.ok(photoService.getPhoto(id))
    }

    @Operation(summary = "유저의 모든 사진 조회", description = "특정 유저가 업로드한 모든 사진을 조회합니다.")
    @GetMapping("/user/{userId}")
    fun getUserPhotos(@PathVariable userId: Long): ResponseEntity<List<PhotoResponse>> {
        return ResponseEntity.ok(photoService.getUserPhotos(userId))
    }

    @Operation(summary = "사진 삭제", description = "특정 사진을 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deletePhoto(@PathVariable id: Long): ResponseEntity<Void> {
        photoService.deletePhoto(id)
        return ResponseEntity.noContent().build()
    }
}