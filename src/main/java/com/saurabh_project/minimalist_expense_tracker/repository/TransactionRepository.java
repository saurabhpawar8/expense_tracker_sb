package com.saurabh_project.minimalist_expense_tracker.repository;

import com.saurabh_project.minimalist_expense_tracker.model.Category;
import com.saurabh_project.minimalist_expense_tracker.model.Transaction;
import com.saurabh_project.minimalist_expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByDateBetween(LocalDate pair, LocalDate pair1);
//
////    List<Transaction> findByDateBetweenAndCategory(LocalDate pair, LocalDate pair1, Category categoryObj);
//
//    List<Transaction> findByCategory(Category categoryObj);
//
//    List<Transaction> findByOrderByIdDesc();
//
////    List<Transaction> findByDateBetweenAndCategoryByOrderByIdDesc(LocalDate pair, LocalDate pair1, Category categoryObj);
////
////    List<Transaction> findByDateBetweenByOrderByIdDesc(LocalDate pair, LocalDate pair1);
//
//
//
//    List<Transaction> findByCategoryOrderByIdDesc(Category categoryObj);
//
//    List<Transaction> findByDateBetweenOrderByIdDesc(LocalDate pair, LocalDate pair1);
//
//    List<Transaction> findByDateBetweenAndCategoryOrderByIdDesc(LocalDate pair, LocalDate pair1, Category categoryObj);
//
//    List<Transaction> findByDateBetweenAndUser_Id(LocalDate pair, LocalDate pair1, Long userId);
//
//

    Transaction findByIdAndUser(Long id, User user);

    List<Transaction> findByUserOrderByIdDesc(User user);

    List<Transaction> findByCategoryAndUserOrderByIdDesc(Category categoryObj, User user);

    List<Transaction> findByDateBetweenAndUserOrderByIdDesc(LocalDate pair, LocalDate pair1, User user);

    List<Transaction> findByDateBetweenAndCategoryAndUserOrderByIdDesc(LocalDate pair, LocalDate pair1, Category categoryObj, User user);

//
//    List<Transaction> findTop20ByOrderByIdDesc();
}