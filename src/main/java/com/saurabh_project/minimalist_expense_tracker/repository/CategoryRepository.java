package com.saurabh_project.minimalist_expense_tracker.repository;

import com.saurabh_project.minimalist_expense_tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT c.name FROM Category c WHERE c.user.id = :userId")
    List<String> findCategoryNames(@Param("userId") Long userId);

    Category findByIdAndUserId(Long id, Long userId);

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId")
    List<Category> findCategoryByUserId(@Param("userId") Long userId);

//    boolean existsByNameAndIdNot(String name, Long id);

//    Optional<Object> findByIdAndUserId(Long id);
}
