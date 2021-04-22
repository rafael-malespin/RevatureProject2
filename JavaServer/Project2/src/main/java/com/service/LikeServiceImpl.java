package com.service;

import com.dao.LikeDao;
import com.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("likeService")
public class LikeServiceImpl implements LikeService{

    private LikeDao likeDao;

    public LikeServiceImpl() { }
    public LikeServiceImpl(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public LikeDao getLikeDao() {
        return likeDao;
    }
    @Autowired
    public void setLikeDao(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    /**
     * Passes Like object to DAO layer to be inserted.
     * @param like Like object
     */
    @Override
    public void insert(Like like) {
        likeDao.insert(like);
    }
    /**
     * Passes Like object to DAO layer to be updated.
     * @param like Like object
     */
    @Override
    public void update(Like like) {
        likeDao.update(like);
    }
    /**
     * Passes Like object to DAO layer to be deleted.
     * @param like Like object
     */
    @Override
    public void delete(Like like) {
        likeDao.delete(like);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Like> selectAll() {
        return likeDao.getAll();
    }
    /**
     * Passes post ID to DAO layer to obtain all Like objects from a post.
     * @param postId Post's unique identifier (Int).
     * @return Array list of Like objects from DAO method.
     */
    @Override
    public List<Like> selectAllForPostId(int postId) {
        return likeDao.getAllForPost(postId);
    }
    /**
     * Passes User ID to DAO layer to obtain all Like instances by a User.
     * @param userId User's identifier (Int).
     * @return Array list of Like objects specific to a User.
     */
    @Override
    public List<Like> selectAllForUserId(int userId) {
        return likeDao.getAllForUser(userId);
    }


}
