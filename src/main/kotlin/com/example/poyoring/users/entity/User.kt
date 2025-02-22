package com.example.poyoring.users.entity

import jakarta.persistence.*
import lombok.*


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(force = true) // 기본 생성자 강제 추가
@AllArgsConstructor
@Builder
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 255)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false, unique = true, length = 50)
    var nickname: String,

    var profileUrl: String?
) {
    fun updateProfile(newNickname: String, newProfileUrl: String?) {
        this.nickname = newNickname
        this.profileUrl = newProfileUrl
    }
}