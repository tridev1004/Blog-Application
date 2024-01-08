package com.example.blogapplication.Services;

import com.example.blogapplication.Payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    ///CREATE
     CategoryDto createCategory(CategoryDto categoryDto);
     // Update
     CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
     // Delete
 void deleteCategory(Integer categoryId);
 //  SINGLE GET
 CategoryDto getCategory(Integer categoryId);
List<CategoryDto> getCategories();
}
