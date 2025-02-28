package com.poyoring.likes.entity

import java.io.Serializable

data class LikeId(
    val userId: Long = 0,
    val feedId: Long = 0
) : Serializable