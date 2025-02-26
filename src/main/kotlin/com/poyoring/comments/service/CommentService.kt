package com.poyoring.comments.service

import com.poyoring.comments.dto.CommentRequest
import com.poyoring.comments.dto.CommentResponse
import com.poyoring.comments.entity.Comment
import com.poyoring.comments.repository.CommentRepository
import com.poyoring.feeds.entity.Feed
import com.poyoring.feeds.repository.FeedRepository
import com.poyoring.users.entity.User
import com.poyoring.users.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val feedRepository: FeedRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createComment(request: CommentRequest): CommentResponse {
        val feed: Feed = feedRepository.findById(request.feedId)
            .orElseThrow { IllegalArgumentException("Feed not found with id: ${request.feedId}") }

        val user: User = userRepository.findById(request.userId)
            .orElseThrow { IllegalArgumentException("User not found with id: ${request.userId}") }

        val comment = Comment(feed = feed, user = user, content = request.content)
        return CommentResponse.from(commentRepository.save(comment))
    }

    fun getCommentsByFeed(feedId: Long): List<CommentResponse> {
        return commentRepository.findByFeedId(feedId).map { CommentResponse.from(it) }
    }

    @Transactional
    fun deleteComment(id: Long) {
        commentRepository.deleteById(id)
    }
}