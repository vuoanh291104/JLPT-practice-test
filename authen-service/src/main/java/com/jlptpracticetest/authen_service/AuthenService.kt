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
    data class RegisterRequest(
        val firebaseToken: String
    )

    data class RegisterResponse(
        val accessToken: String,
        val refreshToken: String,
        val user: Map<String, Any>
    )


    data class RefreshRequest(val refreshToken: String)

    val refreshStore = mutableMapOf<String, String>() // Map uid -> refreshToken

//    @PostMapping("/login")
//    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
//        try{
//            val decoded = try {
//                FirebaseAuth.getInstance().verifyIdToken(request.firebaseToken)
//            } catch (e: Exception) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
//            }
//
//            val uid = decoded.uid
//            val email = decoded.email ?: ""
//
//            // Lấy Firestore instance
//            val firestore = FirestoreClient.getFirestore()
//
//            // Lấy document từ collection "users" với ID là uid
//            val userDoc = firestore.collection("users").document(uid).get().get()
//
//            if (!userDoc.exists()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
//            }
//
//            val role = userDoc.getString("role") ?: "user" // fallback nếu không có trường "role"
//
//            val userData = mapOf("uid" to uid, "email" to email, "role" to role)
//
//            val accessToken = jwtUtil.generateToken(userData)
//            val refreshToken = UUID.randomUUID().toString()
//
//            refreshStore[uid] = refreshToken
//
//            return ResponseEntity.ok(LoginResponse(accessToken, refreshToken, userData))
//        } catch(e: Exception){
//            e.printStackTrace()
//        }
//        return ResponseEntity.ok(LoginResponse("","", mapOf()))
//    }
@PostMapping("/login")
fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
    println("===> [LOGIN] Bắt đầu xác thực...")

    val decoded = try {
        println("===> [LOGIN] Xác thực Firebase token...")
        FirebaseAuth.getInstance().verifyIdToken(request.firebaseToken)
    } catch (e: Exception) {
        println("===> [ERROR] Firebase token không hợp lệ:")
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    val uid = decoded.uid
    val email = decoded.email ?: ""
    println("===> [LOGIN] Firebase token hợp lệ. UID: $uid, Email: $email")

    // Lấy Firestore instance
    val firestore = FirestoreClient.getFirestore()

    // Lấy document từ collection "users" với ID là uid (có timeout)
    val userDoc = try {
        println("===> [LOGIN] Đang truy vấn Firestore với UID: $uid")
        val future = firestore.collection("users").document(uid).get()
        future.get(5, java.util.concurrent.TimeUnit.SECONDS) // timeout 5s
    } catch (e: Exception) {
        println("===> [ERROR] Không thể truy vấn Firestore:")
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }

    if (!userDoc.exists()) {
        println("===> [ERROR] Không tìm thấy user trong Firestore.")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    val role = userDoc.getString("role") ?: "user"
    println("===> [LOGIN] Tìm thấy user. Role: $role")

    val userData = mapOf("uid" to uid, "email" to email, "role" to role)

    val accessToken = jwtUtil.generateToken(userData)
    val refreshToken = UUID.randomUUID().toString()
    refreshStore[uid] = refreshToken

    println("===> [LOGIN] Đăng nhập thành công. Trả về token.")
    return ResponseEntity.ok(LoginResponse(accessToken, refreshToken, userData))
}

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> {
        return try {
            // 1. Xác thực Firebase Token
            val decoded = FirebaseAuth.getInstance().verifyIdToken(request.firebaseToken)
            val uid = decoded.uid
            val email = decoded.email ?: return ResponseEntity.badRequest().build()

            // 2. Lưu thông tin user vào Firestore
            val firestore = FirestoreClient.getFirestore()
            val userData = mapOf(
                "email" to email,
                "role" to "user"
            )
            firestore.collection("users").document(uid).set(userData).get() // chờ hoàn thành

            // 3. Tạo JWT để frontend sử dụng
            val tokenData = mapOf("uid" to uid, "email" to email, "role" to "user")
            val accessToken = jwtUtil.generateToken(tokenData)
            val refreshToken = UUID.randomUUID().toString()
            refreshStore[uid] = refreshToken

            ResponseEntity.ok(RegisterResponse(accessToken, refreshToken, tokenData))
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
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
