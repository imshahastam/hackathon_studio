package com.shaha.hackathon.controller;
import com.shaha.hackathon.security.jwt.JwtUtil;
import com.shaha.hackathon.security.jwt.TokenDTO;
import com.shaha.hackathon.user.CustomUserDetails;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController {
    private final AuthenticationManager manager;
    private final StringHttpMessageConverter stringHttpMessageConverter;

    public LoginController(AuthenticationManager manager, StringHttpMessageConverter stringHttpMessageConverter) {
        this.manager = manager;
        this.stringHttpMessageConverter = stringHttpMessageConverter;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequestDTO loginDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = manager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwtToken = JwtUtil.generateToken(userDetails.getUser());

        return ResponseEntity.ok(new TokenDTO(jwtToken, userDetails.getUsername()));
    }
}
