package com.poyoring.likes.repository

import com.poyoring.likes.entity.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LikeRepository : JpaRepository<Like, Long> {
    fun countByFeedId(feedId: Long): Long
    fun existsByUserIdAndFeedId(userId: Long, feedId: Long): Boolean
    fun deleteByUserIdAndFeedId(userId: Long, feedId: Long)
    @Query("SELECT l.userId FROM Like l WHERE l.feedId = :feedId")
    fun findUsersByFeedId(@Param("feedId") feedId: Long): List<Long>
}