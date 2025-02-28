package com.poyoring.feeds.entity

import jakarta.persistence.Embeddable

@Embeddable
data class FeedPhotoId(
    val feedId: Long,
    val photoId: Long
) : java.io.Serializable
