package com.saurabh_project.minimalist_expense_tracker.repository;

import com.saurabh_project.minimalist_expense_tracker.model.Category;
import com.saurabh_project.minimalist_expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


//    Category findByName(String name, User user);

//    @Query("SELECT c FROM Category c WHERE c.user.id=:userId")
//    Category findByNameAndUser(String name, @Param("userId") Long userId);

    Category findByNameAndUser(String name, User user);
//
//    boolean existsByName(String name);
//
//    boolean existsByNameAndIdNot(String name, Long id);
//
//    @Query("SELECT c.name FROM Category c WHERE c.user.id = :userId")
//    List<String> findCategoryNames(@Param("userId") Long userId);
//
//    Category findByIdAndUserId(Long id, Long userId);
//
//    @Query("SELECT c FROM Category c WHERE c.user.id = :userId")
//    List<Category> findCategoryByUserId(@Param("userId") Long userId);

    boolean existsByNameAndUser(String name, User user);

    boolean existsByNameAndUserAndIdNot(String name, User user, Long id);

    Optional<Category> findByIdAndUser(Long id, User user);

    List<Category> findCategoryByUser(User user);

    @Query("SELECT c.name FROM Category c WHERE c.user = :user")
    List<String> findNamesByUser(@Param("user") User user);

//    boolean existsByNameAndIdNot(String name, Long id);

//    Optional<Object> findByIdAndUserId(Long id);
}
