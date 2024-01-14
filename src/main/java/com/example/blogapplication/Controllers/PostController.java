package com.example.blogapplication.Controllers;

import com.example.blogapplication.Entities.Post;
import com.example.blogapplication.Payloads.ApiResponse;
import com.example.blogapplication.Payloads.PostDto;
import com.example.blogapplication.Services.PostService;
import org.apache.coyote.Response;
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

    //// Get all Posts
@GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPost(){
        List<PostDto> allpost=this.postService.getAllPost();
        return  new ResponseEntity<>(allpost,HttpStatus.OK);
    }

//GetSinglePost
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getAllPost(@PathVariable("postId") Integer postId){
        PostDto post=this.postService.getPostById(postId);
        return  new ResponseEntity<>(post,HttpStatus.OK);
    }

    // DeletePost

    @DeleteMapping("posts/{postId}")
    public ApiResponse deletePost(@PathVariable("postId") Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post is successFullly deleted",true);

    }
    //Update post

    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDto> Update(@PathVariable("postId") Integer postId,PostDto postDto){
        PostDto postDto1 = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }



}
