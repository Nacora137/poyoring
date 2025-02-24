package com.poyoring.users.controller

import com.poyoring.users.dto.UserRequestDto
import com.poyoring.users.dto.UserResponseDto
import com.poyoring.users.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag


@Tag(name = "User API", description = "회원 관리 API")
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    // 회원 조회 (GET)
    @Operation(summary = "회원 조회", description = "회원 ID로 회원 정보를 조회합니다.")
    @GetMapping("/{id}")
    fun getUserById(
        @Parameter(description = "조회할 회원 ID", example = "1") @PathVariable id: Long
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    // 회원 등록 (POST)
    @Operation(summary = "회원 등록", description = "새로운 회원을 등록합니다.")
    @PostMapping
    fun createUser(
        @RequestBody requestDto: UserRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.createUser(requestDto))
    }

    // 회원 수정 (PUT)
    @Operation(summary = "회원 수정", description = "회원 정보를 수정합니다.")
    @PutMapping("/{id}")
    fun updateUser(
        @Parameter(description = "수정할 회원 ID", example = "1") @PathVariable id: Long,
        @RequestBody requestDto: UserRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.updateUser(id, requestDto))
    }

    // 회원 삭제 (DELETE)
    @Operation(summary = "회원 삭제", description = "회원 ID를 기준으로 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deleteUser(
        @Parameter(description = "삭제할 회원 ID", example = "1") @PathVariable id: Long
    ): ResponseEntity<String> {
        userService.deleteUser(id)
        return ResponseEntity.ok("회원 삭제 완료")
    }

}