package com.poyoring.photos.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.poyoring.photos.entity.Photo

interface PhotoRepository : JpaRepository<Photo, Long> {
    fun findByUserId(userId: Long): List<Photo>
}