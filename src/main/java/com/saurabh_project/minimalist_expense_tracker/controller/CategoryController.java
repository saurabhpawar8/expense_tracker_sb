package com.saurabh_project.minimalist_expense_tracker.controller;

import com.saurabh_project.minimalist_expense_tracker.config.JwtService;
import com.saurabh_project.minimalist_expense_tracker.dto.CategoryDto;
import com.saurabh_project.minimalist_expense_tracker.dto.EntityConverter;
import com.saurabh_project.minimalist_expense_tracker.exception.AlreadyExistsException;
import com.saurabh_project.minimalist_expense_tracker.exception.ResourceNotFoundException;
import com.saurabh_project.minimalist_expense_tracker.model.Category;
import com.saurabh_project.minimalist_expense_tracker.request.AddCategoryRequest;
import com.saurabh_project.minimalist_expense_tracker.response.ApiResponse;
import com.saurabh_project.minimalist_expense_tracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final EntityConverter<Category, CategoryDto> entityConverter;
    private final JwtService jwtService;

    public Long getUserId(String token){
        token = token.replace("Bearer ", "");
        return jwtService.extractUserId(token);
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestHeader("Authorization") String token, @RequestBody AddCategoryRequest request) {
        try {
            Long userId = getUserId(token);
            Category category = categoryService.addCategory(request, userId);
            CategoryDto categoryDto = entityConverter.mapEntityToDto(category, CategoryDto.class);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Succefully created!", categoryDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@RequestHeader("Authorization") String token,@RequestBody AddCategoryRequest request, @PathVariable Long id) {
        try {
            Long userId = getUserId(token);
            Category category = categoryService.updateCategory(request, id, userId);
            CategoryDto categoryDto = entityConverter.mapEntityToDto(category, CategoryDto.class);
            return ResponseEntity.status(OK).body(new ApiResponse("Succefully updated!", categoryDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategory(@RequestHeader("Authorization") String token,@PathVariable Long id) {
        try {
            Long userId = getUserId(token);
            Category category = categoryService.getCategoryById(id, userId);
            CategoryDto categoryDto = entityConverter.mapEntityToDto(category, CategoryDto.class);
            return ResponseEntity.ok(new ApiResponse("Found", categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@RequestHeader("Authorization") String token,@PathVariable Long id) {
        try {
            Long userId = getUserId(token);
            categoryService.deleteCategory(id,userId);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(@RequestHeader("Authorization") String token) {
        try {
            Long userId = getUserId(token);
            List<CategoryDto> categoryDtos = categoryService.allCategories(userId);
            return ResponseEntity.ok(new ApiResponse("Found", categoryDtos));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getListOfCategories(@RequestHeader("Authorization") String token) {
        try {
            Long userId = getUserId(token);
            List<String> categories = categoryService.listOfCategories(userId);
            return ResponseEntity.ok(new ApiResponse("Found", categories));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }
}
