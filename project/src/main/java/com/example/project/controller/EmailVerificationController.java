package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.project.EmailSenderService;

import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class EmailVerificationController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/sendVerificationCode")
    public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> payload, HttpSession session) {
        String email = payload.get("email");
        Random random = new Random();
        int verificationCode = 100000 + random.nextInt(900000);

        // Store the code in the session
        session.setAttribute("verificationCode", verificationCode);

        String messageBody = "윙크 | 인증코드입니다: " + verificationCode;
        
        emailSenderService.sendSimpleEmail(email, messageBody, "Verification Code");

        return ResponseEntity.ok().body(Map.of("message", "Verification code sent successfully."));
    }

    
    
    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> payload, HttpSession session) {
        String userCode = payload.get("code");
        Integer sessionCode = (Integer) session.getAttribute("verificationCode");

        if (userCode != null && sessionCode != null && userCode.equals(String.valueOf(sessionCode))) {
            return ResponseEntity.ok(Map.of("verification", "successful"));
        } else {
            return ResponseEntity.ok(Map.of("verification", "failed"));
        }
    }
}