package com.saurabh_project.minimalist_expense_tracker.service;

import com.saurabh_project.minimalist_expense_tracker.config.JwtService;
import com.saurabh_project.minimalist_expense_tracker.exception.AlreadyExistsException;
import com.saurabh_project.minimalist_expense_tracker.model.Role;

import com.saurabh_project.minimalist_expense_tracker.model.User;
import com.saurabh_project.minimalist_expense_tracker.repository.UserRepository;
import com.saurabh_project.minimalist_expense_tracker.request.UserLoginRequest;
import com.saurabh_project.minimalist_expense_tracker.request.UserRegistrationRequest;
import com.saurabh_project.minimalist_expense_tracker.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.saurabh_project.minimalist_expense_tracker.model.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(request.getEmail() + " Already exists!");

        }
//        var user  = User.builder()
//                .username(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .roles(String.valueOf(Role.USER))
//                .build();
//        userRepository.save(user);


        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(USER);
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user, user.getId());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();


    }

//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(username);
//        if (user == null){
//            throw new UsernameNotFoundException("User not Fouund"));
//        }
//        return user;
//    }

    public AuthenticationResponse authenticate(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user, user.getId());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

}
