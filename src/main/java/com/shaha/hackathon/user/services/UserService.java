package com.shaha.hackathon.user.services;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.dto.HackathonParticipantDTO;
import com.shaha.hackathon.judge.models.Competence;
import com.shaha.hackathon.judge.models.Judge;
import com.shaha.hackathon.judge.models.dto.JudgeRegistrationDTO;
import com.shaha.hackathon.judge.services.SaveNewAndExistingTagsService;
import com.shaha.hackathon.repo.*;
import com.shaha.hackathon.team.Team;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.dto.UserInfoDTO;
import com.shaha.hackathon.user.dto.UserRegistrationRequest;
import com.shaha.hackathon.user.roles.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JudgeRepository judgeRepository;
    private final HackathonRepository hackathonRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder encoder;
    private final SaveNewAndExistingTagsService tagsResolveService;

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

    public ResponseEntity<List<HackathonParticipantDTO>> getParticipantsByHackathonId(Long hackId) {
        Hackathon hackathon = hackathonRepository.findById(hackId)
                .orElseThrow(() -> new EntityNotFoundException("Hackathon not found with id: " + hackId));

        List<Team> teams = teamRepository.findAllByHackathonId(hackId);

        Map<Long, String> userIdToTeamName = new HashMap<>();
        for (Team team : teams) {
            String teamName = team.getName();
            Long leaderId = team.getLeader().getId();
            userIdToTeamName.put(leaderId, teamName);
            for (User member : team.getMembers()) {
                if (!Objects.equals(member.getId(), leaderId)) userIdToTeamName.put(member.getId(), teamName);
            }
        }

        List<HackathonParticipantDTO> result = hackathon.getParticipants().stream()
                .map(user -> new HackathonParticipantDTO(
                        user,
                        userIdToTeamName.getOrDefault(user.getId(), null)
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
