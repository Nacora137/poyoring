package com.poyoring.photos.entity

import jakarta.persistence.*

@Entity
@Table(name = "photos")
data class Photo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val filename: String,

    val fileUrl: String,  // 로컬 저장 경로 또는 클라우드 URL

    var description: String
)