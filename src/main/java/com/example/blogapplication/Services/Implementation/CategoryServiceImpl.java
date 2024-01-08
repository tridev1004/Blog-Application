package com.example.blogapplication.Services.Implementation;

import com.example.blogapplication.Entities.Category;
import com.example.blogapplication.Exceptions.ResourceNotFoundException;
import com.example.blogapplication.Payloads.CategoryDto;
import com.example.blogapplication.Repositories.CategoryRepo;
import com.example.blogapplication.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
    Category cat=this.modelMapper.map(categoryDto, Category.class);  // converting dto to category
   Category addedCat =this.categoryRepo.save(cat);
return this.modelMapper.map(addedCat,CategoryDto.class);    // back converting category to category dto
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
       Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category Id",categoryId));
cat.setCategoryTitle(categoryDto.getCategoryTitle());
cat.setCategoryDescription(categoryDto.getCategoryDescription());
Category updatedCat=this.categoryRepo.save(cat);
return this.modelMapper.map(updatedCat,CategoryDto.class);

    }

    @Override
    public void deleteCategory(Integer categoryId) {
Category cat=this.categoryRepo.findById(categoryId).orElseThrow(
        ()->new ResourceNotFoundException("Category", "Category ID",categoryId)
);
this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
       Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category Id",categoryId));
  return this.modelMapper.map(cat,CategoryDto.class);   /// class to dto conversion
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
      List<CategoryDto> categoryDtos=  categories.stream().map(category -> this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
return categoryDtos;

    }
}
