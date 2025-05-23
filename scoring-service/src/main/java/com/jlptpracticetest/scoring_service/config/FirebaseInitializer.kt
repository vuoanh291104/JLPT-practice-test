package com.jlptpracticetest.scoring_service.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.FileInputStream

@Component
    class FirebaseInitializer {
    init {
        val serviceAccount = ClassPathResource("firebase/jlpt-practice-test-firebase-adminsdk-fbsvc-db90114924.json").inputStream
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        FirebaseApp.initializeApp(options)
    }
}
