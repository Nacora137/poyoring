package com.poyoring.photos.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "photos")
class Photo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "filename", length = 100)
    var filename: String? = null,

    @Column(name = "file_url", length = 255)
    var fileUrl: String? = null,

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    val uploadedAt: LocalDateTime = LocalDateTime.now()
)