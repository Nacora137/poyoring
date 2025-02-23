package com.poyoring.users.repository

import com.poyoring.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

}