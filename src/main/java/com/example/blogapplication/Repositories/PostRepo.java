package com.example.blogapplication.Repositories;

import com.example.blogapplication.Entities.Category;
import com.example.blogapplication.Entities.Post;
import com.example.blogapplication.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);


    List<Post> findByCategory(Category category);



    List <Post> findByTitleContaining(String title);






}
