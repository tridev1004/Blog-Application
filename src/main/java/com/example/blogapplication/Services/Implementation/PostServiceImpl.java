package com.example.blogapplication.Services.Implementation;

import com.example.blogapplication.Entities.Category;
import com.example.blogapplication.Entities.Post;
import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Exceptions.ResourceNotFoundException;
import com.example.blogapplication.Payloads.PostDto;
import com.example.blogapplication.Repositories.CategoryRepo;
import com.example.blogapplication.Repositories.PostRepo;
import com.example.blogapplication.Repositories.UserRep;
import com.example.blogapplication.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired

    private PostRepo postRepo;
    @Autowired
    private UserRep userRep;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto ceatePost(PostDto postDto,Integer userId,Integer categoryId) {
        User user=this.userRep.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User_Id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category_id",categoryId));

        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post newPost = this.postRepo.save(post);
       return this.modelMapper.map(newPost,PostDto.class);


    }

    @Override
    public Post updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<Post> getallPost() {
        return null;
    }

    @Override
    public Post getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category_id",categoryId));
      List<Post> posts= this.postRepo.findByCategory(cat);
List<PostDto> postDtos=      posts.stream().map((post->this.modelMapper.map(posts,PostDto.class))).collect(Collectors.toList());


        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
       User user= this.userRep.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user_id",userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<Post> searchPost(String keyword) {
        return null;
    }
}