package com.shaha.hackathon.controller;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonDTO;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.UserDTO;
import com.shaha.hackathon.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerNewUser(@RequestBody UserDTO user) {
        return userService.registerNewUserService(user);
    }
}
