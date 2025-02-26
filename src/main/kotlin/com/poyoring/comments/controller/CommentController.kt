package com.poyoring.comments.controller

import com.poyoring.comments.dto.CommentRequest
import com.poyoring.comments.dto.CommentResponse
import com.poyoring.comments.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment API", description = "댓글 관련 API")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    @Operation(summary = "댓글 생성", description = "새로운 댓글을 생성합니다.")
    fun createComment(@RequestBody request: CommentRequest): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.createComment(request))
    }

    @GetMapping("/feed/{feedId}")
    @Operation(summary = "피드 댓글 조회", description = "특정 피드의 모든 댓글을 조회합니다.")
    fun getCommentsByFeed(@PathVariable feedId: Long): ResponseEntity<List<CommentResponse>> {
        return ResponseEntity.ok(commentService.getCommentsByFeed(feedId))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    fun deleteComment(@PathVariable id: Long): ResponseEntity<Void> {
        commentService.deleteComment(id)
        return ResponseEntity.noContent().build()
    }
}