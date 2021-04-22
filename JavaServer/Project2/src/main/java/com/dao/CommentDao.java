package com.dao;



import com.model.Comment;

import java.util.List;

public interface CommentDao {

    public void insert(Comment comment);
    public void update(Comment comment);
    public void delete(Comment comment);
    public Comment getCommentByID(int id);
    public List<Comment> selectAllComments();
    public List<Comment> getAllForPost(int postId);
    public List<Comment> getAllForUser(int userId);
}
