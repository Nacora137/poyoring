package com.poyoring.feeds.entity

import com.poyoring.photos.entity.Photo
import jakarta.persistence.*

@Embeddable
data class FeedPhotoId(
    val feedId: Long,
    val photoId: Long
) : java.io.Serializable

@Entity
@Table(name = "feed_photos")
class FeedPhoto(
    @EmbeddedId
    val id: FeedPhotoId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("feedId")
    @JoinColumn(name = "feed_id")
    val feed: Feed,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("photoId")
    @JoinColumn(name = "photo_id")
    val photo: Photo
)