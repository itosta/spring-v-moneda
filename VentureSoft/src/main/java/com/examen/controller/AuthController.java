package com.examen.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examen.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Aquí deberías validar contra tu base de datos o UserDetailsService
        if ("admin".equals(username) && "1234".equals(password)) {
            return jwtUtil.generateToken(username);
        }
        throw new RuntimeException("Credenciales inválidas");
    }
}