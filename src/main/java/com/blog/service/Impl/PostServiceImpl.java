package com.blog.service.Impl;
import com.blog.entities.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private  PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
       Post post=mapToEntity(postDto);

        Post savedPost = postRepository.save(post);

      PostDto dto= mapToDto(savedPost);
        return dto;
    }
  @Override
  public PostDto updatePost(PostDto postDto,long id){
       Post post= postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post", "id",id));
       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
       post.setDescription(postDto.getDescription());

      Post  updatePost = postRepository.save(post);

      return mapToDto(updatePost);
  }
  @Override
  public void deletePost(long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id:"+id, "id", id)
        );
        postRepository.deleteById(id);
  }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize ,String sortBy,String sortDir) {
       Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
               Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

       PageRequest pageable= PageRequest.of(pageNo,pageSize, sort);
        Page<Post> content= postRepository.findAll(pageable);
        List <Post> posts = content.getContent();

       List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElements(content.getTotalElements());
        postResponse.setLast(content.isLast());
        return postResponse;

    }
  @Override
public PostDto getPostById(long id){
    Post post = postRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Post Not Found with id: " + id, "id", id)
    );
    PostDto postDto = mapToDto(post);
    return postDto;
}
   Post mapToEntity(PostDto postDto){
      Post post=  mapper.map(postDto,Post.class);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    PostDto mapToDto(Post post){
     PostDto dto=   mapper.map(post, PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }
}
