package com.dao;

import com.model.Comment;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("commentDao")
@Transactional
public class CommentDaoImpl implements CommentDao{

    private SessionFactory sesFact;

    @Override
    public void insert(Comment comment) {
        sesFact.getCurrentSession().save(comment);
    }

    @Override
    public void update(Comment comment) {
        sesFact.getCurrentSession().update(comment);
    }

    @Override
    public void delete(Comment comment) {
        sesFact.getCurrentSession().delete(comment);
    }

    @Override
    public Comment getCommentByID(int id) {
        return sesFact.getCurrentSession().get(Comment.class,id);
    }

    @Override
    public List<Comment> selectAllComments() {
        return sesFact.getCurrentSession().createQuery("from Comment",Comment.class).list();
    }

    @Override
    public List<Comment> getAllForPost(int postId) {
        return null;
    }

    @Override
    public List<Comment> getAllForUser(int userId) {
        return null;
    }

    public CommentDaoImpl() {
    }

    public CommentDaoImpl(SessionFactory sesFact) {
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
