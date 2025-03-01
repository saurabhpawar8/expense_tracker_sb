package com.saurabh_project.minimalist_expense_tracker.repository;

import com.saurabh_project.minimalist_expense_tracker.model.Category;
import com.saurabh_project.minimalist_expense_tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByDateBetween(LocalDate pair, LocalDate pair1);

//    List<Transaction> findByDateBetweenAndCategory(LocalDate pair, LocalDate pair1, Category categoryObj);

    List<Transaction> findByCategory(Category categoryObj);

    List<Transaction> findByOrderByIdDesc();

//    List<Transaction> findByDateBetweenAndCategoryByOrderByIdDesc(LocalDate pair, LocalDate pair1, Category categoryObj);
//
//    List<Transaction> findByDateBetweenByOrderByIdDesc(LocalDate pair, LocalDate pair1);



    List<Transaction> findByCategoryOrderByIdDesc(Category categoryObj);

    List<Transaction> findByDateBetweenOrderByIdDesc(LocalDate pair, LocalDate pair1);

    List<Transaction> findByDateBetweenAndCategoryOrderByIdDesc(LocalDate pair, LocalDate pair1, Category categoryObj);

//
//    List<Transaction> findTop20ByOrderByIdDesc();
}