package com.service;

import com.dao.PostDao;
import com.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postService")
public class PostServiceImpl implements PostService{

    private PostDao postDao;

    public PostServiceImpl() { }
    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }

    public PostDao getPostDao() {
        return postDao;
    }
    @Autowired
    public void setPostDao(PostDao postDao){
        this.postDao=postDao;
    }

    /**
     * Passes Post object to DAO layer to be inserted.
     * @param post Post object
     */
    @Override
    public void insert(Post post) {
        postDao.insert(post);
    }
    /**
     * Passes Post object to DAO layer to be updated.
     * @param post Post object
     */
    @Override
    public void update(Post post) {
        postDao.update(post);
    }
    /**
     * Passes Post object to DAO layer to be deleted.
     * @param post Post object
     */
    @Override
    public void delete(Post post) {
        postDao.delete(post);
    }
    /**
     * Passes ID to DAO layer to obtain Post object.
     * @param id Post's unique identifier (Int).
     * @return Post object from DAO method.
     */
    @Override
    public Post selectById(int id) {
        return postDao.getPostById(id);
    }
    /**
     * Calls DAO layer method to pull all existing Post objects.
     * @return Array list of all Post objects.
     */
    @Override
    public List<Post> selectAllPosts() {
        return postDao.getAllPosts();
    }
    /**
     * Passes User ID to DAO layer to obtain all User's Post objects.
     * @param userId User's identifier (Int).
     * @return Array list of all post of selected User.
     */
    @Override
    public List<Post> selectAllByUser(int userId) {
        return postDao.getAllPostsCreatedByUser(userId);
    }

    /**
     * Passes User ID to DAO layer to obtain all liked posts by a user.
     * @param userId User's Identifier (Int).
     * @return Array list of all posts liked by a User.
     */
    @Override
    public List<Post> selectAllLikedByUser(int userId) {
        return postDao.getAllLikedPostsByUser(userId);
    }

}
