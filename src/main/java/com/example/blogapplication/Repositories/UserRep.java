package com.example.blogapplication.Repositories;

import com.example.blogapplication.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRep extends JpaRepository<User,Integer> {

}
