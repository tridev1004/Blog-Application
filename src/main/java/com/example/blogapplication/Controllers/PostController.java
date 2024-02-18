package com.example.blogapplication.Controllers;

import com.example.blogapplication.Config.AppConstants;
import com.example.blogapplication.Payloads.ApiResponse;
import com.example.blogapplication.Payloads.PostDto;
import com.example.blogapplication.Payloads.PostResponse;
import com.example.blogapplication.Services.FileService;
import com.example.blogapplication.Services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

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

    // Get all Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.Page_Number,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.Page_Size,required = false)Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.Sort_By,required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.Sort_dir ,required = false)String sortDir


            )
    {
        PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return  new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //GetSinglePost
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable("postId") Integer postId){
        PostDto post=this.postService.getPostById(postId);
        return  new ResponseEntity<>(post,HttpStatus.OK);
    }

    // DeletePost

    @DeleteMapping("posts/{postId}")
    public ApiResponse deletePost(@PathVariable("postId") Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post is successFully deleted",true);

    }
    //Update post

    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDto> Update(@PathVariable("postId") Integer postId,PostDto postDto){
        PostDto postDto1 = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    // search in post
@GetMapping("posts/search/{keywords}")
public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
        List<PostDto> result=this.postService.searchPost(keywords);
      return new ResponseEntity<>(result,HttpStatus.OK);
}

// post image upload

@PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @PathVariable("postId") Integer postId,
        @RequestParam("image")MultipartFile image) throws IOException {
    PostDto postDto = this.postService.getPostById(postId);

    String fileName = this.fileService.UploadImage(path, image);
    postDto.setImageName(fileName);
    PostDto updatedPost = this.postService.updatePost(postDto, postId);


    return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);

}

@GetMapping("/post/image/{imageName}")
  public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {

    InputStream resource=this.fileService.getResource(path,imageName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource,response.getOutputStream());

  }




}
