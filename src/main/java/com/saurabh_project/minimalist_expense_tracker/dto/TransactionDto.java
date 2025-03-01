package com.saurabh_project.minimalist_expense_tracker.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long id;
    private String category;
    private String date;
    private String time;
    private BigDecimal amount;
    private String notes;
}
