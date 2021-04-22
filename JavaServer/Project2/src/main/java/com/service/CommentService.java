package com.service;

import com.model.Comment;

import java.util.List;

public interface CommentService {
    void insert(Comment comment);
    void update(Comment comment);
    void delete(Comment comment);
    List<Comment> selectAllComments();

}
