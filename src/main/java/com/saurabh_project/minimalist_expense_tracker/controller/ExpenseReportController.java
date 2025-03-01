package com.saurabh_project.minimalist_expense_tracker.controller;

import com.saurabh_project.minimalist_expense_tracker.config.JwtService;
import com.saurabh_project.minimalist_expense_tracker.service.ExpenseReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/download")
@RequiredArgsConstructor
@RestController
public class ExpenseReportController {
    private final ExpenseReportService expenseReportService;
    private final JwtService jwtService;

    public Long getUserId(String token){
        token = token.replace("Bearer ", "");
        return jwtService.extractUserId(token);
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExpensesInExcel(@RequestHeader("Authorization") String token, @RequestParam String dateRange,
                                                          @RequestParam(required = false) String startDate,
                                                          @RequestParam(required = false) String endDate) throws IOException {

        Long userId = getUserId(token);
        byte[] bytes = expenseReportService.generateExpenseExcel(dateRange, startDate, endDate,userId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expense.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }
}