package com.poyoring.likes.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@IdClass(LikeId::class)  // ✅ 복합 키 지정
@Table(name = "likes")
data class Like(
    @Id
    val userId: Long,

    @Id
    val feedId: Long,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)

data class LikeId(
    val userId: Long = 0,
    val feedId: Long = 0
) : Serializable