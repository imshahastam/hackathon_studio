package com.shaha.hackathon.user;

import com.shaha.hackathon.repo.RoleRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.roles.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
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

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.error("Authentication object is null");
            throw new RuntimeException("Ошибка аутентификации");
        }

        Object principal = authentication.getPrincipal();
        log.info("Principal: {}", principal);

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            log.info("Extracted username: {}", username);
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        }

        log.error("Principal is not instance of UserDetails");
        throw new RuntimeException("Ошибка аутентификации");

    }
}
