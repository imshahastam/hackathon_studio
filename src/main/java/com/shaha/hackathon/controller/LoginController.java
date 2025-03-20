package com.shaha.hackathon.controller;

import com.shaha.hackathon.security.jwt.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationManager manager;
    private final StringHttpMessageConverter stringHttpMessageConverter;

    public LoginController(AuthenticationManager manager, StringHttpMessageConverter stringHttpMessageConverter) {
        this.manager = manager;
        this.stringHttpMessageConverter = stringHttpMessageConverter;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody com.shaha.hackathon.user.User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        );

        Authentication authentication = manager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = JwtUtil.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(jwtToken);
    }
}
