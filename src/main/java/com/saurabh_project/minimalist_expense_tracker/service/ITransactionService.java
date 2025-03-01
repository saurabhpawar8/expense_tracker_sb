package com.saurabh_project.minimalist_expense_tracker.service;

import com.saurabh_project.minimalist_expense_tracker.dto.TransactionDto;
import com.saurabh_project.minimalist_expense_tracker.model.Transaction;
import com.saurabh_project.minimalist_expense_tracker.request.AddTransactionRequest;

import java.util.List;

public interface ITransactionService {
    Transaction addTransaction(AddTransactionRequest request,Long userId);

    Transaction updateTransaction(AddTransactionRequest request, Long id,Long userId);

    void deleteTransaction(Long id,Long userId);

    List<TransactionDto> allTransactions(String dateRange, String startDate, String endDate, String category,Long userId);

    Transaction getTransactionById(Long id,Long userId);


}
