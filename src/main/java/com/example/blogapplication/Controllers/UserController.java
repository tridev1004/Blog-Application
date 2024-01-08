package com.example.blogapplication.Controllers;

import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Payloads.ApiResponse;
import com.example.blogapplication.Payloads.UserDto;
import com.example.blogapplication.Services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;


    // user data is coming and catching it using requestbody
    // @valid is used to enable tha valdiation in usedto like Notnull or else
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
     /// putting or updating the data
    @PutMapping("/{userId}")  //{userId} - path uri variable for this we use pathvariable in this userId is coming
  public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
       UserDto updatedUser=     this.userService.updateUser(userDto,userId);
       return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{userId}")
     public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId) {   // deleting the user using userId coming from postman through uRi
        this.userService.deleteUser(userId);
        return new ResponseEntity(new ApiResponse("user Deleted Succesfully", true), HttpStatus.OK);

    }

        /// getting the user

        @GetMapping("/")
        public ResponseEntity<List<UserDto>> getAllusers(){
return ResponseEntity.ok(this.userService.getAllUsers());
}
@GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId){
        return  ResponseEntity.ok( this.userService.getUserById(userId));
}

}
