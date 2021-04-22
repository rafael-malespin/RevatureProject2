package com.dao;

import com.model.Like;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("likeDao")
@Transactional
public class LikeDaoImpl implements LikeDao{

    private SessionFactory sesFact;

    /**
     * CRUD method (Create) - Inserts Like object into database.
     * @param like Like object initiated by user.
     */
    @Override
    public void insert(Like like) {
        sesFact.getCurrentSession().save(like);
    }

    /**
     * CRUD method (Update) - Updates Like record in database with new Like object.
     * @param like updated Like object.
     */
    @Override
    public void update(Like like) {
        sesFact.getCurrentSession().update(like);
    }

    /**
     * CRUD method (Delete) - Removes Like record in database.
     * @param like Like object to be removed.
     */
    @Override
    public void delete(Like like) {
        sesFact.getCurrentSession().delete(like);
    }

    /**
     * CRUD method (Read) - Pulls Like record from database based on the ID and maps to a Like object in Java.
     * @param id Unique identifier of Like object (also Primary Key in DB).
     * @return Like object based on selected record from database.
     */
    @Override
    public Like getLikeById(int id) {
        return sesFact.getCurrentSession().get(Like.class,id);
    }

    /**
     *CRUD method (Read) - Pulls all Like records from database.
     * @return Array List of all Like records.
     */
    @Override
    public List<Like> getAll() {
        return sesFact.getCurrentSession().createQuery("from Like",Like.class).list();
    }

    /**
     * CRUD method (Read) - Pulls all like records with a specific post ID and maps to Java list of Like objects.
     * @param postId Identifier of specific post (also Foreign Key in DB).
     * @return Array list of Like objects that are associated with a specific post.
     */
    @Override
    public List<Like> getAllForPost(int postId) {
        return sesFact.getCurrentSession().createQuery("from Like where post_FK="+postId,Like.class).list();
    }

    /**
     *CRUD method (Read) - Pulls all Like records from the database for a specific User.
     * @param userId User's indentifier (Int).
     * @return Array List of Like objects specific to a User.
     */
    @Override
    public List<Like> getAllForUser(int userId) {
        return sesFact.getCurrentSession().createQuery("from Like where user_FK="+userId,Like.class).list();
    }


    public LikeDaoImpl() {
    }

    public LikeDaoImpl(SessionFactory sesFact) {
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
