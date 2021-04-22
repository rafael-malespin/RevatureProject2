package com.service;

import com.dao.CommentDao;
import com.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao;
    @Override
    public void insert(Comment comment) {
        commentDao.insert(comment);
    }

    @Override
    public void update(Comment comment) {
        commentDao.update(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    @Override
    public List<Comment> selectAllComments() {
        return commentDao.selectAllComments();
    }

    public CommentServiceImpl() {
    }

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public CommentDao getCommentDao() {
        return commentDao;
    }

    @Autowired
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

}
