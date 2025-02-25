package com.poyoring.feeds.service

import com.poyoring.feeds.dto.FeedRequest
import com.poyoring.feeds.dto.FeedResponse
import com.poyoring.feeds.entity.Feed
import com.poyoring.feeds.entity.FeedPhoto
import com.poyoring.feeds.entity.FeedPhotoId
import com.poyoring.feeds.repository.FeedPhotoRepository
import com.poyoring.feeds.repository.FeedRepository
import com.poyoring.photos.repository.PhotoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FeedService(
    private val feedRepository: FeedRepository,
    private val photoRepository: PhotoRepository,
    private val feedPhotoRepository: FeedPhotoRepository
) {

    @Transactional
    fun createFeed(request: FeedRequest): FeedResponse {
        val feed = Feed(userId = request.userId, content = request.content)
        val savedFeed = feedRepository.save(feed)

        val photos = photoRepository.findAllById(request.photoIds)
        val feedPhotos = photos.map { photo ->
            FeedPhoto(FeedPhotoId(savedFeed.id!!, photo.id!!), savedFeed, photo)
        }
        feedPhotoRepository.saveAll(feedPhotos)

        return toFeedResponse(savedFeed, feedPhotos)
    }

    @Transactional
    fun deleteFeed(id: Long) {
        feedRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    fun getFeed(id: Long): FeedResponse {
        val feed = feedRepository.findById(id).orElseThrow { RuntimeException("Feed not found") }
        val photos = feedPhotoRepository.findByIdFeedId(id)
        return toFeedResponse(feed, photos)
    }

    private fun toFeedResponse(feed: Feed, feedPhotos: List<FeedPhoto>): FeedResponse {
        return FeedResponse(
            id = feed.id!!,
            userId = feed.userId,
            content = feed.content,
            photoIds = feedPhotos.map { it.id.photoId }
        )
    }
}