package com.shaha.hackathon.user.services;

import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.judge.models.Judge;
import com.shaha.hackathon.judge.models.dto.JudgeRegistrationDTO;
import com.shaha.hackathon.judge.services.SaveNewAndExistingTagsService;
import com.shaha.hackathon.repo.JudgeRepository;
import com.shaha.hackathon.repo.RoleRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.dto.UserRegistrationRequest;
import com.shaha.hackathon.user.roles.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JudgeRepository judgeRepository;
    private final PasswordEncoder encoder;
    private final SaveNewAndExistingTagsService tagsResolveService;

    public UserService(RoleRepository roleRepository,
                       UserRepository userRepository,
                       JudgeRepository judgeRepository,
                       PasswordEncoder encoder,
                       SaveNewAndExistingTagsService tagsResolveService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.judgeRepository = judgeRepository;
        this.encoder = encoder;
        this.tagsResolveService = tagsResolveService;
    }

    public ResponseEntity<String> registerNewUserService(UserRegistrationRequest request) {
        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByNameOfRole(roleName)
                        .orElseThrow(() -> new RuntimeException("Роль не найдена: " + roleName)))
                .collect(Collectors.toSet());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        // 2. Если роль JUDGE, создаём Judge и связываем
        if (request.getRoles().contains("JUDGE") && request.getJudge() != null) {
            JudgeRegistrationDTO judgeDTO = request.getJudge();
            Judge judge = new Judge();
            judge.setUser(savedUser);
            judge.setCompany(judgeDTO.getCompany());
            judge.setWorkExperience(judgeDTO.getWorkExperience());
            judge.setBio(judgeDTO.getBio());
            judge.setLinkedin(judgeDTO.getLinkedin());

            Set<Competence> competences = tagsResolveService.execute(judgeDTO.getTagsId(), judgeDTO.getNewTags());
            judge.setCompetences(competences);

            judgeRepository.save(judge);
        }

        return ResponseEntity.ok().build();

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
