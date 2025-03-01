package com.saurabh_project.minimalist_expense_tracker.service;

import com.saurabh_project.minimalist_expense_tracker.model.User;
import com.saurabh_project.minimalist_expense_tracker.request.UserRegistrationRequest;
import com.saurabh_project.minimalist_expense_tracker.response.AuthenticationResponse;

public interface IUserService {
    AuthenticationResponse registerUser(UserRegistrationRequest request);

}