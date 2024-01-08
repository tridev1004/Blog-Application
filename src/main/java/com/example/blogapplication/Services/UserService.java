package com.example.blogapplication.Services;

import com.example.blogapplication.Payloads.UserDto;

import java.util.List;

public interface UserService {
UserDto createUser(UserDto user);
UserDto updateUser(UserDto user, Integer userId);
UserDto getUserById(Integer userId);
List<UserDto> getAllUsers();
void deleteUser(Integer userId);
}
