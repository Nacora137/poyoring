package com.poyoring.comments.dto

data class CommentRequest(
    val feedId: Long,
    val userId: Long,
    val content: String
)