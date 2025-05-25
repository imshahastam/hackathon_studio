package com.shaha.hackathon.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaha.hackathon.judge.models.dto.JudgeRegistrationDTO;
import lombok.Data;

import java.util.List;
@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private List<String> roles;
    private JudgeRegistrationDTO judge;
}
