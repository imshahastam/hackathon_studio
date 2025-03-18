package com.shaha.hackathon.user;

import com.shaha.hackathon.repo.RoleRepository;
import com.shaha.hackathon.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserService(RoleRepository roleRepository,
                                UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }
}
