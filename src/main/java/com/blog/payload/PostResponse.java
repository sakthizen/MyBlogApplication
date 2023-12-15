package com.blog.payload;

import lombok.Data;

import javax.swing.text.AbstractDocument;
import java.util.List;
import java.util.Set;
@Data
public class PostResponse {
    private List<PostDto> content;
    private  int  pageNo;
    private  int pageSize;
    private  long totalElements;
    private  int  totalPages;
    private  boolean isLast;





}
