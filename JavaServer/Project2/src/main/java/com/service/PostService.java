package com.service;

import com.dao.PostDao;
import com.model.Post;

import java.util.List;

public interface PostService {
    public void insert(Post post);
    public void update(Post post);
    public void delete(Post post);
    public Post selectById(int id);
    public List<Post> selectAllPosts();
    public List<Post> selectAllByUser(int userId);
    public List<Post> selectAllLikedByUser(int userId);
    public void setPostDao(PostDao postDao);
}
