package com.blog.payload;

import com.blog.entities.Comment;
import lombok.Data;

@Data
public class CommentDto  {
    private long id;
    private String name;
    private String email;
    private String body;

}
