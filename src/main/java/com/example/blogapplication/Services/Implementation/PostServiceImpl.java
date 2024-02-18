package com.example.blogapplication.Services.Implementation;

import com.example.blogapplication.Entities.Category;
import com.example.blogapplication.Entities.Post;
import com.example.blogapplication.Entities.User;
import com.example.blogapplication.Exceptions.ResourceNotFoundException;
import com.example.blogapplication.Payloads.PostDto;
import com.example.blogapplication.Payloads.PostResponse;
import com.example.blogapplication.Repositories.CategoryRepo;
import com.example.blogapplication.Repositories.PostRepo;
import com.example.blogapplication.Repositories.UserRep;
import com.example.blogapplication.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "Post_id", postId));
      post.setTitle(postDto.getTitle());
      post.setContent(postDto.getContent());
      post.setImageName(postDto.getImageName());

      Post UpdatedPost=this.postRepo.save(post);
        return this.modelMapper.map(UpdatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "Post_id", postId));
           this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?
                Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable p= PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());

        Page<Post> PagePost = this.postRepo.findAll(p);

       List<Post> allPosts=PagePost.getContent();



       List<PostDto> postDtos= allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
       PostResponse postResponse=new PostResponse();
       postResponse.setContent(postDtos);
       postResponse.setPageNumber(PagePost.getNumber());
       postResponse.setPageSize(PagePost.getSize());
       postResponse.setTotalElements(PagePost.getTotalElements());
       postResponse.setTotalPages(PagePost.getTotalPages());
       postResponse.setLastPage(PagePost.isLast());


        return postResponse;

    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post_id",postId));
        return this.modelMapper.map(post,PostDto.class);

    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category_id",categoryId));
      List<Post> posts= this.postRepo.findByCategory(cat);
List<PostDto> postDtos=  posts.stream().map((post->this.modelMapper.map(post,PostDto.class))).collect(Collectors.toList());


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
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
