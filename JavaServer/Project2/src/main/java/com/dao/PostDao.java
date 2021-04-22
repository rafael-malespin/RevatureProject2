package com.dao;

import com.model.Post;

import java.util.List;

public interface PostDao {
    public void insert(Post post);
    public void update(Post post);
    public void delete(Post post);
    public Post getPostById(int id);
    public List<Post> getAllPosts();
    public List<Post> getAllLikedPostsByUser(int userId);
    public List<Post> getAllPostsCreatedByUser(int userId);
}
