package com.saurabh_project.minimalist_expense_tracker.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddTransactionRequest {
    private String category;
    private String date;
    private String time;
    private BigDecimal amount;
    private String notes;
}