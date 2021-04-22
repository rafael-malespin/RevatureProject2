package com.service;

import com.dao.LikeDao;
import com.model.Like;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface LikeService {
    public void insert(Like like);
    public void update(Like like);
    public void delete(Like like);
    public List<Like> selectAll();
    public List<Like> selectAllForPostId(int postId);
    public List<Like> selectAllForUserId(int userId);

    @Autowired
    void setLikeDao(LikeDao likeDao);
}
