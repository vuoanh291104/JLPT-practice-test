//package com.jlptpracticetest.exam_service.jwt
//
//import jakarta.servlet.FilterChain
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.stereotype.Component
//import org.springframework.web.filter.OncePerRequestFilter
//
//@Component
//class JwtFilter(val jwtUtil: JwtUtil) : OncePerRequestFilter() {
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        val authHeader = request.getHeader("Authorization")
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            val token = authHeader.removePrefix("Bearer ")
//            try {
//                val claims = jwtUtil.validateToken(token)
//                request.setAttribute("user", claims)
//            } catch (e: Exception) {
//                response.status = HttpServletResponse.SC_UNAUTHORIZED
//                return
//            }
//        } else {
//            response.status = HttpServletResponse.SC_UNAUTHORIZED
//            return
//        }
//
//        filterChain.doFilter(request, response)
//    }
//}
