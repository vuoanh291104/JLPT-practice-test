package com.jlptpracticetest.authen_service
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import java.util.*

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secret: String
}

@Component
class JwtUtil(@Autowired val jwtProperties: JwtProperties) {

    private val expiration = 1000 * 60 * 15 // 15 phút

    fun generateToken(data: Map<String, Any>): String {
        return Jwts.builder()
            .setClaims(data)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
            .compact()
    }

    fun validateToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(jwtProperties.secret)
            .parseClaimsJws(token)
            .body
    }
}
