package com.example.blogapplication.Services;

import com.example.blogapplication.Entities.Post;
import com.example.blogapplication.Payloads.PostDto;

import java.util.List;

public interface PostService {
    // create
    PostDto ceatePost(PostDto postDto, Integer userId,Integer categoryId);
    // update
    Post updatePost (PostDto postDto,Integer postId);
    // delete

    void deletePost(Integer postId);
    // get all post
    List<Post> getallPost();
    // get single post
    Post getPostById(Integer postId);

    // get all post by category

List<PostDto> getPostByCategory(Integer categoryId);
//get all post by user

    List<PostDto>getPostByUser(Integer userId);
    // search post
    List<Post> searchPost(String keyword);



}
