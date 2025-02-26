package com.poyoring.comments.dto

import com.poyoring.comments.entity.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val feedId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id,
                feedId = comment.feed.id ?: throw IllegalStateException("Feed ID cannot be null"),
                userId = comment.user.id ?: throw IllegalStateException("User ID cannot be null"),
                content = comment.content,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt
            )
        }
    }
}