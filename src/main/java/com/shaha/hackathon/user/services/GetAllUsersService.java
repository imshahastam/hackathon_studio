package com.shaha.hackathon.user.services;

import com.shaha.hackathon.Query;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsersService implements Query<Void, List<UserDTO>> {
    private final UserRepository userRepository;

    public GetAllUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute(Void input) {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(UserDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    }
}
