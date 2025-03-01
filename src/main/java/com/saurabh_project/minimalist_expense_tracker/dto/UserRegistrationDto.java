package com.saurabh_project.minimalist_expense_tracker.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String mobile;
}