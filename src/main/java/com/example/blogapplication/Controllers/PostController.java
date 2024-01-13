package com.example.blogapplication.Controllers;

import com.example.blogapplication.Entities.Post;
import com.example.blogapplication.Payloads.PostDto;
import com.example.blogapplication.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
// Creating a post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("categoryId")
    Integer categoryId,@PathVariable("userId")Integer userId){
      PostDto createPost=  this.postService.ceatePost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

}

// get by user

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId")Integer userId){

       List<PostDto> posts= this.postService.getPostByUser(userId);
       return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);



    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId")Integer categoryId){

        List<PostDto> posts= this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);



    }


}
