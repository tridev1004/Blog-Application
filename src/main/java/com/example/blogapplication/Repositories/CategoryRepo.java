package com.example.blogapplication.Repositories;

import com.example.blogapplication.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
