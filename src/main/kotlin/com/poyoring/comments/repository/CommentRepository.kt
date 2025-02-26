package com.poyoring.comments.repository

import com.poyoring.comments.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByFeedId(feedId: Long): List<Comment>
}