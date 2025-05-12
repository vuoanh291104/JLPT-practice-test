package com.jlptpracticetest.authen_service
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.Claims

import java.util.*

object JwtUtil {
    private const val SECRET_KEY = "your-secret-key"
    private val expiration = 1000 * 60 * 15 // 15 phút

    fun generateToken(data: Map<String, Any>): String {
        return Jwts.builder()
            .setClaims(data)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact()
    }

    fun validateToken(token: String): Claims =
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
}
