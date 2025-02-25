package com.poyoring.feeds.controller

import com.poyoring.feeds.dto.FeedRequest
import com.poyoring.feeds.dto.FeedResponse
import com.poyoring.feeds.service.FeedService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Feed API", description = "Feed 및 사진 관리 API")
@RestController
@RequestMapping("/api/feeds")
class FeedController(private val feedService: FeedService) {

    @Operation(summary = "Feed 생성", description = "새로운 Feed를 생성합니다.")
    @PostMapping
    fun createFeed(@RequestBody request: FeedRequest): ResponseEntity<FeedResponse> {
        return ResponseEntity.ok(feedService.createFeed(request))
    }

    @Operation(summary = "Feed 조회", description = "ID로 특정 Feed를 조회합니다.")
    @GetMapping("/{id}")
    fun getFeed(@PathVariable id: Long): ResponseEntity<FeedResponse> {
        return ResponseEntity.ok(feedService.getFeed(id))
    }

    @Operation(summary = "Feed 삭제", description = "ID로 특정 Feed를 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deleteFeed(@PathVariable id: Long): ResponseEntity<Void> {
        feedService.deleteFeed(id)
        return ResponseEntity.noContent().build()
    }
}