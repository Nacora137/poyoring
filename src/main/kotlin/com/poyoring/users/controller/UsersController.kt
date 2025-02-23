package com.poyoring.users.controller

import com.poyoring.users.dto.UserRequestDto
import com.poyoring.users.dto.UserResponseDto
import com.poyoring.users.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/users")
class UsersController(private val userService: UserService) {

    // 회원 등록 (POST)
    @PostMapping
    fun createUser(@RequestBody requestDto: UserRequestDto): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.createUser(requestDto))
    }

    // 회원 수정 (PUT)
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody requestDto: UserRequestDto): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.updateUser(id, requestDto))
    }

    // 회원 삭제 (DELETE)
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> {
        userService.deleteUser(id)
        return ResponseEntity.ok("회원 삭제 완료")
    }

}