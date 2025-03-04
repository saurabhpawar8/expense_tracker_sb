package com.saurabh_project.minimalist_expense_tracker.controller;

import com.saurabh_project.minimalist_expense_tracker.config.JwtService;
import com.saurabh_project.minimalist_expense_tracker.dto.EntityConverter;
import com.saurabh_project.minimalist_expense_tracker.dto.TransactionDto;


import com.saurabh_project.minimalist_expense_tracker.exception.AlreadyExistsException;
import com.saurabh_project.minimalist_expense_tracker.model.Transaction;
import com.saurabh_project.minimalist_expense_tracker.request.AddTransactionRequest;
import com.saurabh_project.minimalist_expense_tracker.response.ApiResponse;
import com.saurabh_project.minimalist_expense_tracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final EntityConverter<Transaction, TransactionDto> entityConverter;
    private final JwtService jwtService;

    public Long getUserId(String token){
        token = token.replace("Bearer ", "");
        return jwtService.extractUserId(token);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransaction(@RequestHeader("Authorization") String token,@RequestBody AddTransactionRequest request) {
        try {
            Long userId = getUserId(token);
            Transaction transaction = transactionService.addTransaction(request,userId);
            TransactionDto transactionDto = entityConverter.mapEntityToDto(transaction, TransactionDto.class);
            transactionDto.setCategory(transaction.getCategory().getName());
            return ResponseEntity.status(CREATED).body(new ApiResponse("Succefully created!", transactionDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateTransaction(@RequestHeader("Authorization") String token,@RequestBody AddTransactionRequest request, @PathVariable Long id) {
        try {
            Long userId = getUserId(token);
            Transaction transaction = transactionService.updateTransaction(request, id,userId);
            TransactionDto transactionDto = entityConverter.mapEntityToDto(transaction, TransactionDto.class);
            transactionDto.setCategory(transaction.getCategory().getName());
            return ResponseEntity.status(OK).body(new ApiResponse("Succefully updated!", transactionDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTransaction(@RequestHeader("Authorization") String token,@PathVariable Long id) {
        try {
            Long userId = getUserId(token);
            Transaction transaction = transactionService.getTransactionById(id,userId);
            TransactionDto transactionDto = entityConverter.mapEntityToDto(transaction, TransactionDto.class);
            transactionDto.setCategory(transaction.getCategory().getName());
            return ResponseEntity.ok(new ApiResponse("Found", transactionDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteTransaction(@RequestHeader("Authorization") String token,@PathVariable Long id) {
        try {
            Long userId = getUserId(token);
            transactionService.deleteTransaction(id,userId);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(@RequestHeader("Authorization") String token,@RequestParam(required = false) String dateRange,
                                                        @RequestParam(required = false) String startDate,
                                                        @RequestParam(required = false) String endDate,
                                                        @RequestParam(required = false) String category) {
        try {
            Long userId = getUserId(token);
            List<TransactionDto> transactionDtos = transactionService.allTransactions(dateRange, startDate, endDate, category,userId);
            return ResponseEntity.ok(new ApiResponse("Found", transactionDtos));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }


}