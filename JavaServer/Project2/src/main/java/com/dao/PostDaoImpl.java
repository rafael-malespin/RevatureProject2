package com.dao;

import com.model.Post;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("postDao")
@Transactional
public class PostDaoImpl implements PostDao{

    private SessionFactory sesFact;

    /**
     * CRUD method (Create) - Inserts post object into database.
     * @param post Post object initiated by user.
     */
    @Override
    public void insert(Post post) {
        sesFact.getCurrentSession().save(post);
    }
    /**
     * CRUD method (Update) - Updates Post record in database with new Post object.
     * @param post updated Post object.
     */
    @Override
    public void update(Post post) {
        sesFact.getCurrentSession().update(post);
    }
    /**
     * CRUD method (Delete) - Removes Post record in database.
     * @param post Post object to be removed.
     */
    @Override
    public void delete(Post post) {
        sesFact.getCurrentSession().delete(post);
    }
    /**
     * CRUD method (Read) - Pulls Post record from database based on the ID and maps to a Post object in Java.
     * @param id Unique identifier of Post object (also Primary Key in DB).
     * @return Post object based on selected record from database.
     */
    @Override
    public Post getPostById(int id) {
        return sesFact.getCurrentSession().get(Post.class,id);
    }

    /**
     * CRUD method (Read) - Pulls all Post records from database and maps to Java list of Post objects.
     * @return Array list of Post objects.
     */
    @Override
    public List<Post> getAllPosts() {
        return sesFact.getCurrentSession().createQuery("from Post",Post.class).list();
    }

    //TODO: implement getting all posts liked by a user
    @Override
    public List<Post> getAllLikedPostsByUser(int userId) {
        return null;
    }

    /**
     * CRUD method (Read) - Pulls all Post records from database based on specific user and maps to
     * Java list of Post objects.
     * @param userId Identifier of specific user (also Foreign Key in DB).
     * @return Array List of Post objects.
     */
    @Override
    public List<Post> getAllPostsCreatedByUser(int userId) {
        return sesFact.getCurrentSession().createQuery("from Post where creator_user_id="+userId,Post.class).list();
    }


    public PostDaoImpl() {
    }

    public PostDaoImpl(SessionFactory sesFact) {
        this.sesFact = sesFact;
    }

    public SessionFactory getSesFact() {
        return sesFact;
    }
    @Autowired
    public void setSesFact(SessionFactory sesFact) {
        this.sesFact = sesFact;
    }
}
