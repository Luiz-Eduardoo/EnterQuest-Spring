package com.startup.enterquest.config;

import java.io.FileInputStream;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Service
public class FirebaseConfig {

    @PostConstruct
    public void inicializarFirebase() throws Exception {

        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/enterq-713ab-firebase-adminsdk-fbsvc-1869586637.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}