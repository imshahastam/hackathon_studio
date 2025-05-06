package com.shaha.hackathon.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaha.hackathon.hackathon.model.Hackathon;
import com.shaha.hackathon.user.User;
import com.shaha.hackathon.user.roles.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    String username;
    String password;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;
    Set<Long> hackathonsIds;
    Set<String> roles;

    public UserDTO() {}

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.hackathonsIds = user.getHackathons().stream()
                .map(Hackathon::getId)
                .collect(Collectors.toSet());
        this.roles = user.getRoles().stream()
                .map(Role::getNameOfRole)
                .collect(Collectors.toSet());
    }
}
