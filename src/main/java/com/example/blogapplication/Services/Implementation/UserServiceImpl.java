package com.example.blogapplication.Services.Implementation;

import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Exceptions.ResourceNotFoundException;
import com.example.blogapplication.Payloads.UserDto;
import com.example.blogapplication.Repositories.UserRep;
import com.example.blogapplication.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRep userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        // in this function the conversion is happening and returning the saved data in database
        User user = this.dtotoUser(userDto);

        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);  /// here we are returning the dto object not the user the user is saved in databse if we ex[ose user obj to client its not safe so that's why wwe are using userdto

    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updateUser = this.userRepo.save(user);
        UserDto userdto1 = this.userToDto(updateUser);
        return userdto1;


    }

    @Override
    public UserDto getUserById(Integer userId) {


        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.userToDto(user);

    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();
        /// getting all users through stream and collecting them
        // using lambda stream api to convert list of user to list of userdto
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;

    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);

    }

    private User dtotoUser(UserDto userDto) {
//        User user=new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());                // here converting the data transfer object user to ,main user without exposing user
//        user.setAbout(userDto.getAbout());                  // dto- >user or entities
//        user.setPassword(userDto.getPassword());
//        return user;

        User user = this.modelMapper.map(userDto, User.class);      /// here conversion is done in one line no need for all the upper code just use ModelMapper dependency and declare it as a bean
        return user;


    }

    public UserDto userToDto(User user) {
//        UserDto userDto=new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());          // here setting use dto to user for saving in database
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());                   // user->dto
//        userDto.setPassword(user.getPassword());

        UserDto userDto = this.modelMapper.map(user, UserDto.class);   /// same hapenning here just modelmapper used


        return userDto;
    }
}
