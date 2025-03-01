package com.saurabh_project.minimalist_expense_tracker.service;

import com.saurabh_project.minimalist_expense_tracker.dto.EntityConverter;
import com.saurabh_project.minimalist_expense_tracker.dto.TransactionDto;
import com.saurabh_project.minimalist_expense_tracker.exception.ResourceNotFoundException;
import com.saurabh_project.minimalist_expense_tracker.model.Category;
import com.saurabh_project.minimalist_expense_tracker.model.Transaction;
import com.saurabh_project.minimalist_expense_tracker.repository.CategoryRepository;
import com.saurabh_project.minimalist_expense_tracker.repository.TransactionRepository;
import com.saurabh_project.minimalist_expense_tracker.request.AddTransactionRequest;
import com.saurabh_project.minimalist_expense_tracker.utils.DateRange;
import lombok.RequiredArgsConstructor;
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
    private final EntityConverter<Transaction, TransactionDto> entityConverter;

    @Override
    public Transaction addTransaction(AddTransactionRequest request,Long userId) {
        Category category = categoryRepository.findByName(request.getCategory());
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

        return transactionRepository.save(transaction);

    }

    @Override
    public Transaction updateTransaction(AddTransactionRequest request, Long id,Long userId) {
        Category category = categoryRepository.findByName(request.getCategory());
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
    public void deleteTransaction(Long id,Long userId) {
        transactionRepository.findById(id)
                .ifPresentOrElse(transactionRepository::delete, () -> {
                    throw new ResourceNotFoundException("Transaction not found");
                });
    }

    @Override
    public List<TransactionDto> allTransactions(String dateRange, String startDate, String endDate, String category,Long userId) {
        System.out.println(dateRange);
        List<Transaction> transactions = List.of();
        if (dateRange != null && category != null) {

            LocalDate[] pairs = new DateRange().determineDateRanges(dateRange, startDate, endDate);
            Category category_obj = categoryRepository.findByName(category);
            transactions = transactionRepository.findByDateBetweenAndCategoryOrderByIdDesc(pairs[0], pairs[1], category_obj);

        } else if (dateRange == null && category == null) {
            transactions = transactionRepository.findByOrderByIdDesc();
        } else if (category == null) {
            LocalDate[] pairs = new DateRange().determineDateRanges(dateRange, startDate, endDate);
            transactions = transactionRepository.findByDateBetweenOrderByIdDesc(pairs[0], pairs[1]);
        } else if (dateRange == null) {
            Category category_obj = categoryRepository.findByName(category);
            transactions = transactionRepository.findByCategoryOrderByIdDesc(category_obj);
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
    public Transaction getTransactionById(Long id,Long userId) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not Found"));
    }
}

