package com.saurabh_project.minimalist_expense_tracker.service;

import com.saurabh_project.minimalist_expense_tracker.dto.CategoryDto;
import com.saurabh_project.minimalist_expense_tracker.model.Category;
import com.saurabh_project.minimalist_expense_tracker.request.AddCategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category addCategory(AddCategoryRequest request, Long userId);

    Category updateCategory(AddCategoryRequest request, Long id,Long userId);

    void deleteCategory(Long id, Long userId);

    List<CategoryDto> allCategories(Long userId);

    Category getCategoryById(Long id,Long userId);

    List<String> listOfCategories(Long userId);
}
