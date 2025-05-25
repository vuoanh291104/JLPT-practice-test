package com.jlptpracticetest.exam_service.jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Claims
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Base64

@Component
class JwtUtil(val jwtProperties: JwtProperties) {

    fun validateToken(token: String): Claims {
        val decodedSecret = Base64.getDecoder().decode(jwtProperties.secret)
        val key = Keys.hmacShaKeyFor(decodedSecret)
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
