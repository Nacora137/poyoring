package com.poyoring.likes.service

import com.poyoring.likes.dto.LikeResponseDto
import com.poyoring.likes.dto.UserLikeDto
import com.poyoring.likes.entity.Like
import com.poyoring.likes.repository.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeService(private val likeRepository: LikeRepository) {
    fun likeFeed(userId: Long, feedId: Long) {
        if (!likeRepository.existsByUserIdAndFeedId(userId, feedId)) {
            likeRepository.save(Like(userId, feedId))
        }
    }

    fun unlikeFeed(userId: Long, feedId: Long) {
        likeRepository.deleteByUserIdAndFeedId(userId, feedId)
    }

    fun getLikeCount(feedId: Long): LikeResponseDto {
        val count = likeRepository.countByFeedId(feedId)
        return LikeResponseDto(feedId, count)  // ✅ DTO 객체를 명확하게 생성
    }

    fun getUsersWhoLiked(feedId: Long): List<UserLikeDto> =
        likeRepository.findUsersByFeedId(feedId).map { UserLikeDto(it) }
}