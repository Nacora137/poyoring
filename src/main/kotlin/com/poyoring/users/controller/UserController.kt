package com.poyoring.users.controller

import com.poyoring.users.dto.UserRequestDto
import com.poyoring.users.dto.UserResponseDto
import com.poyoring.users.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


@Tag(name = "User API", description = "회원 관리 API")
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping("/detect-face")
    fun detectFace(@RequestParam imageUrl: String): String {
        return try {
            // 프로젝트 내부 `.venv`의 Python 실행 파일 경로 설정
            val projectRoot = File("").absolutePath
            val pythonCommand = File("$projectRoot/src/main/python/face-checker/.venv/bin/python3").absolutePath

            // Python 실행 파일 경로
            val scriptPath = File("$projectRoot/src/main/python/face-checker/faceChecker.py").absolutePath

            // 실행할 명령어 조합
            val processBuilder = ProcessBuilder(pythonCommand, scriptPath, imageUrl)

            // 실행할 디렉토리 설정 (Python 실행 파일이 있는 곳으로 설정)
            processBuilder.directory(File("$projectRoot/src/main/python/face-checker/"))

            // 실행 및 출력 가져오기
            val process = processBuilder.start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))

            // JSON 데이터 수집
            val jsonOutput = StringBuilder()
            reader.forEachLine { jsonOutput.append(it) }

            // 에러 로그 수집
            val errorOutput = StringBuilder()
            errorReader.forEachLine { errorOutput.append(it) }

            // 프로세스 종료 코드 확인
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                println("✅ Python 실행 성공")
                println("📌 JSON 응답: $jsonOutput")

                // JSON 파싱
                val jsonResponse = JSONObject(jsonOutput.toString())
                return jsonResponse.toString()
            } else {
                println("❌ Python 실행 실패 (종료 코드: $exitCode)")
                println("🚨 오류 메시지: $errorOutput")
                return JSONObject(mapOf("error" to "Python 실행 오류", "details" to errorOutput.toString())).toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return JSONObject(mapOf("error" to "서버 내부 오류", "details" to e.message)).toString()
        }
    }

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