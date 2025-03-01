package com.saurabh_project.minimalist_expense_tracker.controller;


import com.saurabh_project.minimalist_expense_tracker.request.UserLoginRequest;
import com.saurabh_project.minimalist_expense_tracker.request.UserRegistrationRequest;
import com.saurabh_project.minimalist_expense_tracker.response.AuthenticationResponse;
import com.saurabh_project.minimalist_expense_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegistrationRequest request) {
        AuthenticationResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserLoginRequest request) {
        AuthenticationResponse response = userService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
