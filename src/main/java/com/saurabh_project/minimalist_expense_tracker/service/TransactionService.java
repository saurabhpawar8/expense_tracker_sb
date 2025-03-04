package com.saurabh_project.minimalist_expense_tracker.service;

import com.saurabh_project.minimalist_expense_tracker.dto.EntityConverter;
import com.saurabh_project.minimalist_expense_tracker.dto.TransactionDto;
import com.saurabh_project.minimalist_expense_tracker.exception.ResourceNotFoundException;
import com.saurabh_project.minimalist_expense_tracker.model.Category;
import com.saurabh_project.minimalist_expense_tracker.model.Transaction;
import com.saurabh_project.minimalist_expense_tracker.model.User;
import com.saurabh_project.minimalist_expense_tracker.repository.CategoryRepository;
import com.saurabh_project.minimalist_expense_tracker.repository.TransactionRepository;
import com.saurabh_project.minimalist_expense_tracker.repository.UserRepository;
import com.saurabh_project.minimalist_expense_tracker.request.AddTransactionRequest;
import com.saurabh_project.minimalist_expense_tracker.utils.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EntityConverter<Transaction, TransactionDto> entityConverter;

    @Override
    public Transaction addTransaction(AddTransactionRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Category category = categoryRepository.findByNameAndUser(request.getCategory(), user);
        if (category == null) {
            throw new ResourceNotFoundException("Category not Found");
        }
        LocalDate date;
        LocalTime time;
        try {
            date = LocalDate.parse(request.getDate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            time = LocalTime.parse(request.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Transaction transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setDate(date);
        transaction.setTime(time);
        transaction.setAmount(request.getAmount());
        transaction.setNotes(request.getNotes());
        transaction.setUser(user);

        return transactionRepository.save(transaction);

    }

    @Override
    public Transaction updateTransaction(AddTransactionRequest request, Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Category category = categoryRepository.findByNameAndUser(request.getCategory(),user);
        if (category == null) {
            throw new ResourceNotFoundException("Category not Found");
        }
        LocalDate date;
        LocalTime time;
        try {
            date = LocalDate.parse(request.getDate());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            time = LocalTime.parse(request.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not Found"));
        transaction.setCategory(category);
        transaction.setDate(date);
        transaction.setTime(time);
        transaction.setAmount(request.getAmount());
        transaction.setNotes(request.getNotes());


        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Transaction transaction = transactionRepository.findByIdAndUser(id,user);
        if (transaction!=null){
            transactionRepository.delete(transaction);
        }
    }

    @Override
    public List<TransactionDto> allTransactions(String dateRange, String startDate, String endDate, String category, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Transaction> transactions = List.of();
        if (dateRange != null && category != null) {

            LocalDate[] pairs = new DateRange().determineDateRanges(dateRange, startDate, endDate);
            Category category_obj = categoryRepository.findByNameAndUser(category,user);
            transactions = transactionRepository.findByDateBetweenAndCategoryAndUserOrderByIdDesc(pairs[0], pairs[1], category_obj,user);

        } else if (dateRange == null && category == null) {
            transactions = transactionRepository.findByUserOrderByIdDesc(user);
        } else if (category == null) {
            LocalDate[] pairs = new DateRange().determineDateRanges(dateRange, startDate, endDate);
            transactions = transactionRepository.findByDateBetweenAndUserOrderByIdDesc(pairs[0], pairs[1],user);
        } else if (dateRange == null) {
            Category category_obj = categoryRepository.findByNameAndUser(category,user);
            transactions = transactionRepository.findByCategoryAndUserOrderByIdDesc(category_obj,user);
        }
        return transactions.stream().map(transaction -> {
                    TransactionDto dto = entityConverter
                            .mapEntityToDto(transaction, TransactionDto.class);
                    if (transaction.getCategory() != null) {
                        dto.setCategory(transaction.getCategory().getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Transaction getTransactionById(Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return transactionRepository.findByIdAndUser(id,user);
    }
}

