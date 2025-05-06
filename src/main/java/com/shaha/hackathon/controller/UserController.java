package com.shaha.hackathon.controller;

import com.shaha.hackathon.user.dto.UserDTO;
import com.shaha.hackathon.user.services.UserService;
import com.shaha.hackathon.user.services.GetAllUsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final GetAllUsersService getAllUsersService;

    public UserController(UserService userService, GetAllUsersService getAllUsersService) {
        this.userService = userService;
        this.getAllUsersService = getAllUsersService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registerNewUser(@RequestBody UserDTO user) {
        return userService.registerNewUserService(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return getAllUsersService.execute(null);
    }
}
