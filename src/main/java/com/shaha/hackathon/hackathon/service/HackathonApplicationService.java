package com.shaha.hackathon.hackathon.service;

import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.hackathon.model.HackathonApplication;
import com.shaha.hackathon.hackathon.model.HackathonApplicationId;
import com.shaha.hackathon.repo.HackathonApplicationRepository;
import com.shaha.hackathon.repo.HackathonRepository;
import com.shaha.hackathon.repo.UserRepository;
import com.shaha.hackathon.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HackathonApplicationService {

    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;
    private final HackathonApplicationRepository applicationRepository;

    public HackathonApplicationService(UserRepository userRepository,
                                       HackathonRepository hackathonRepository,
                                       HackathonApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.hackathonRepository = hackathonRepository;
        this.applicationRepository = applicationRepository;
    }

    public void apply(Long userId, Long hackathonId) {
        User user = userRepository.findById(userId).orElseThrow();
        Hackathon hackathon = hackathonRepository.findById(Math.toIntExact(hackathonId)).orElseThrow();

        HackathonApplicationId appId = new HackathonApplicationId();
        appId.setUserId(userId);
        appId.setHackathonId(hackathonId);

        if (applicationRepository.existsById(appId)) {
            throw new RuntimeException("Already applied");
        }

        HackathonApplication application = new HackathonApplication();
        application.setId(appId);
        application.setUser(user);
        application.setHackathon(hackathon);
        application.setStatus(HackathonApplication.ApplicationStatus.PENDING);

        applicationRepository.save(application);
    }

    public void approve(Long userId, Long hackathonId) {
        HackathonApplication app = getApplication(userId, hackathonId);
        app.setStatus(HackathonApplication.ApplicationStatus.APPROVED);

        // Связываем
        User user = app.getUser();
        Hackathon hackathon = app.getHackathon();
        user.getHackathons().add(hackathon);
        userRepository.save(user);

        applicationRepository.save(app);
    }

    public void decline(Long userId, Long hackathonId) {
        HackathonApplication app = getApplication(userId, hackathonId);
        app.setStatus(HackathonApplication.ApplicationStatus.DECLINED);
        applicationRepository.save(app);
    }

    public List<HackathonApplication> getPendingApplications(Long hackathonId) {
        return applicationRepository.findByHackathonIdAndStatus(
                hackathonId, HackathonApplication.ApplicationStatus.PENDING
        );
    }

    private HackathonApplication getApplication(Long userId, Long hackathonId) {
        HackathonApplicationId id = new HackathonApplicationId();
        id.setUserId(userId);
        id.setHackathonId(hackathonId);
        return applicationRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Application not found")
        );
    }

    public boolean hasUserApplied(Long userId, Long hackathonId) {
        HackathonApplicationId appId = new HackathonApplicationId();
        appId.setUserId(userId);
        appId.setHackathonId(hackathonId);

        return applicationRepository.existsById(appId);
    }
}

