package com.poyoring.likes.controller

import com.poyoring.likes.dto.LikeRequestDto
import com.poyoring.likes.dto.LikeResponseDto
import com.poyoring.likes.dto.UserLikeDto
import com.poyoring.likes.service.LikeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Like API", description = "피드 좋아요 관련 API")
@RestController
@RequestMapping("/likes")
class LikeController(private val likeService: LikeService) {
    @Operation(summary = "피드 좋아요 추가")
    @PostMapping("/{feedId}")
    fun like(@RequestBody request: LikeRequestDto, @PathVariable feedId: Long): ResponseEntity<Void> {
        likeService.likeFeed(request.userId, feedId)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "피드 좋아요 취소")
    @DeleteMapping("/{feedId}")
    fun unlike(@RequestBody request: LikeRequestDto, @PathVariable feedId: Long): ResponseEntity<Void> {
        likeService.unlikeFeed(request.userId, feedId)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "특정 피드의 좋아요 개수 조회")
    @GetMapping("/{feedId}/count")
    fun getLikeCount(@PathVariable feedId: Long): ResponseEntity<LikeResponseDto> {
        return ResponseEntity.ok(likeService.getLikeCount(feedId))
    }

    @Operation(summary = "특정 피드에 좋아요한 사용자 목록 조회")
    @GetMapping("/{feedId}/users")
    fun getUsersWhoLiked(@PathVariable feedId: Long): ResponseEntity<List<UserLikeDto>> {
        return ResponseEntity.ok(likeService.getUsersWhoLiked(feedId))
    }
}