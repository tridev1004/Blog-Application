package com.example.blogapplication.Controllers;

import com.example.blogapplication.Payloads.ApiResponse;
import com.example.blogapplication.Payloads.CategoryDto;
import com.example.blogapplication.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //CREATE
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory( @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);

    }


    //UPDATE
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> UpdateCategory(@Valid@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer categoryId) {
        CategoryDto CategoryUpdate = this.categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.ok(CategoryUpdate);
    }


    //DELTE
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> DeleteCategory(@PathVariable("categoryId") Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return new  ResponseEntity<ApiResponse>(new ApiResponse("Category is Deleted Successfull",true),HttpStatus.OK);

    }


    //GET
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> GetCategory(@PathVariable("categoryId") Integer categoryId) {
     CategoryDto GetCategory=   this.categoryService.getCategory(categoryId);
        return new  ResponseEntity<CategoryDto>(GetCategory,HttpStatus.OK);

    }
    //GET ALL
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> GetAllCategory() {
        List<CategoryDto> Categories=   this.categoryService.getCategories();
        return   ResponseEntity.ok(Categories);

    }
}
