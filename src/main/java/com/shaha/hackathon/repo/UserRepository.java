package com.shaha.hackathon.repo;

import com.shaha.hackathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
