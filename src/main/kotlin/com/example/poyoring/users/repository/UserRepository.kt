package com.example.poyoring.users.repository

import com.example.poyoring.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RequestMapping
interface UserRepository : JpaRepository<User, Long> {

}