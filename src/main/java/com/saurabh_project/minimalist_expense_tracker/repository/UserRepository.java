package com.saurabh_project.minimalist_expense_tracker.repository;

import com.saurabh_project.minimalist_expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    User findByEmail(String username);
}
