package com.shaha.hackathon.repo;

import com.shaha.hackathon.user.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameOfRole(String nameOfRole);
}
