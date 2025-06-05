package com.shaha.hackathon.hackathon.model;

import com.shaha.hackathon.user.User;
import jakarta.persistence.*;

@Entity
public class HackathonApplication {

    @EmbeddedId
    private HackathonApplicationId id = new HackathonApplicationId();

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("hackathonId")
    private Hackathon hackathon;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public enum ApplicationStatus {
        PENDING, APPROVED, DECLINED
    }

    public HackathonApplicationId getId() {
        return id;
    }

    public void setId(HackathonApplicationId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hackathon getHackathon() {
        return hackathon;
    }

    public void setHackathon(Hackathon hackathon) {
        this.hackathon = hackathon;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
