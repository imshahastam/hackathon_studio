package com.shaha.hackathon.repo;

import com.shaha.hackathon.user.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
