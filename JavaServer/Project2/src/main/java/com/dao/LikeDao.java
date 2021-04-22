package com.dao;

import com.model.Like;

import java.util.List;

public interface LikeDao {
    public void insert(Like like);
    public void update(Like like);
    public void delete(Like like);
    public Like getLikeById(int id);
    public List<Like> getAll();
    public List<Like> getAllForPost(int postId);
    public List<Like> getAllForUser(int userId);
}
