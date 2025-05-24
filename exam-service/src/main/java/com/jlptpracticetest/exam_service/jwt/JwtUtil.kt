//package com.jlptpracticetest.exam_service.jwt
//
//import org.springframework.stereotype.Component
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.Claims
//
//@Component
//class JwtUtil(val jwtProperties: JwtProperties) {
//
//    fun validateToken(token: String): Claims {
//        return Jwts.parser()
//            .setSigningKey(jwtProperties.secret)
//            .parseClaimsJws(token)
//            .body
//    }
//}
