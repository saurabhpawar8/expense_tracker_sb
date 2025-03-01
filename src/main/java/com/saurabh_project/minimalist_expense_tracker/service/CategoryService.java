package com.saurabh_project.minimalist_expense_tracker.service;

import com.saurabh_project.minimalist_expense_tracker.config.JwtService;
import com.saurabh_project.minimalist_expense_tracker.dto.CategoryDto;
import com.saurabh_project.minimalist_expense_tracker.dto.EntityConverter;
import com.saurabh_project.minimalist_expense_tracker.exception.AlreadyExistsException;
import com.saurabh_project.minimalist_expense_tracker.exception.ResourceNotFoundException;
import com.saurabh_project.minimalist_expense_tracker.model.Category;
import com.saurabh_project.minimalist_expense_tracker.model.User;
import com.saurabh_project.minimalist_expense_tracker.repository.CategoryRepository;
import com.saurabh_project.minimalist_expense_tracker.repository.UserRepository;
import com.saurabh_project.minimalist_expense_tracker.request.AddCategoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final EntityConverter<Category, CategoryDto> entityConverter;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    public Category addCategory(AddCategoryRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        if (categoryRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException(request.getName() + " Already exists!");
        }
        Category category = new Category();
        category.setName(request.getName());
        category.setUser(user);
        return categoryRepository.save(category);


    }


    @Override
    public Category updateCategory(AddCategoryRequest request, Long id, Long userId) {
        if (categoryRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new AlreadyExistsException("Already exists");
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(request.getName() + " Not Found"));
        category.setName(request.getName());
        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(Long id, Long userId) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found");
                });

    }

    @Override
    public List<CategoryDto> allCategories(Long userId) {
        List<Category> categories = categoryRepository.findCategoryByUserId(userId);
        return categories.stream().map(category -> entityConverter
                        .mapEntityToDto(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategoryById(Long id, Long userId) {

        try {
            return categoryRepository.findByIdAndUserId(id, userId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Category not Found");
        }
    }

    @Override
    public List<String> listOfCategories(Long userId) {
        return categoryRepository.findCategoryNames(userId);
    }
}

