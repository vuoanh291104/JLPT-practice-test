package com.jlptpracticetest.authen_service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secret: String // should be base64 encoded
}

@Component
class JwtUtil @Autowired constructor(jwtProperties: JwtProperties) {

    private val expiration = 1000 * 60 * 15 // 15 minutes

    private val secretKey: SecretKey =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret)) // decode base64 string

    fun generateToken(data: Map<String, Any>): String {
        return Jwts.builder()
            .claims(data)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey) // signature algorithm is inferred from key
            .compact()
    }

    fun validateToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey) // verifyWith() expects SecretKey
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
