package com.poyoring.feeds.repository

import com.poyoring.feeds.entity.FeedPhoto
import com.poyoring.feeds.entity.FeedPhotoId
import org.springframework.data.jpa.repository.JpaRepository

interface FeedPhotoRepository : JpaRepository<FeedPhoto, FeedPhotoId> {
    fun findByIdFeedId(feedId: Long): List<FeedPhoto>
}