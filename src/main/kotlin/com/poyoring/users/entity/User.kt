package com.poyoring.users.entity

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
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var username: String? = null,

    @Column(nullable = true, unique = true)
    var email: String? = null,

    @Column(nullable = true)
    var nickname: String? = null,

    @Column(nullable = true)
    var profileUrl: String? = null
)