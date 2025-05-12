package com.jlptpracticetest.authen_service

import com.google.firebase.auth.FirebaseAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/auth")
class AuthController {

    data class LoginRequest(val firebaseToken: String)
    data class LoginResponse(
        val accessToken: String,
        val refreshToken: String,
        val user: Map<String, Any>
    )

    data class RefreshRequest(val refreshToken: String)

    val refreshStore = mutableMapOf<String, String>() // Map uid -> refreshToken

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val decoded = FirebaseAuth.getInstance().verifyIdToken(request.firebaseToken)
        val uid = decoded.uid
        val email = decoded.email ?: ""
        val role = "user" // lấy từ DB nếu cần

        val userData = mapOf("uid" to uid, "email" to email, "role" to role)

        val accessToken = JwtUtil.generateToken(userData)
        val refreshToken = UUID.randomUUID().toString()

        refreshStore[uid] = refreshToken

        return ResponseEntity.ok(LoginResponse(accessToken, refreshToken, userData))
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody request: RefreshRequest): ResponseEntity<Map<String, String>> {
        val uid = refreshStore.entries.find { it.value == request.refreshToken }?.key
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val userData = mapOf("uid" to uid, "email" to "", "role" to "user")
        val newAccessToken = JwtUtil.generateToken(userData)

        return ResponseEntity.ok(mapOf("accessToken" to newAccessToken))
    }

    @GetMapping("/user-exams")
    fun getUserExams(@RequestHeader("Authorization") authHeader: String): ResponseEntity<List<Map<String, Any>>> {
        val token = authHeader.removePrefix("Bearer ")
        val claims = JwtUtil.validateToken(token)

        val uid = claims["uid"] as String
        // giả sử lấy dữ liệu từ DB
        val exams = listOf(
            mapOf("examName" to "Toán", "score" to 8.5),
            mapOf("examName" to "Lý", "score" to 7.0)
        )
        return ResponseEntity.ok(exams)
    }
}
