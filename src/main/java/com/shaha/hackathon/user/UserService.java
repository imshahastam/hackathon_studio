package com.shaha.hackathon.user;

import com.shaha.hackathon.repo.RoleRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.roles.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(RoleRepository roleRepository,
                       UserRepository userRepository,
                       PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<String> registerNewUserService(UserDTO userDTO) {
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleName -> roleRepository.findByNameOfRole(roleName)
                        .orElseThrow(() -> new RuntimeException("Роль не найдена: " + roleName)))
                .collect(Collectors.toSet());

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok("Success");

    }
}
