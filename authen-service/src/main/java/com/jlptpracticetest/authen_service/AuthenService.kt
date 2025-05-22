package com.jlptpracticetest.authen_service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val jwtUtil: JwtUtil
) {

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

        // Lấy Firestore instance
        val firestore = FirestoreClient.getFirestore()

        // Lấy document từ collection "users" với ID là uid
        val userDoc = firestore.collection("users").document(uid).get().get()

        if (!userDoc.exists()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }

        val role = userDoc.getString("role") ?: "user" // fallback nếu không có trường "role"

        val userData = mapOf("uid" to uid, "email" to email, "role" to role)

        val accessToken = jwtUtil.generateToken(userData)
        val refreshToken = UUID.randomUUID().toString()

        refreshStore[uid] = refreshToken

        return ResponseEntity.ok(LoginResponse(accessToken, refreshToken, userData))
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody request: RefreshRequest): ResponseEntity<Map<String, String>> {
        val uid = refreshStore.entries.find { it.value == request.refreshToken }?.key
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val userData = mapOf("uid" to uid, "email" to "", "role" to "user")
        val newAccessToken = jwtUtil.generateToken(userData)

        return ResponseEntity.ok(mapOf("accessToken" to newAccessToken))
    }
}
