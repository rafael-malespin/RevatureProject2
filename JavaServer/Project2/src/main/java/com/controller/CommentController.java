package com.controller;


import com.model.Comment;
import com.model.CustomResponseMessage;
import com.service.CommentService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200",  allowCredentials = "true")
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;
    final static Logger loggy = Logger.getLogger("primaryLogger");
    static {
        loggy.setLevel(Level.ALL);
        //loggy.setLevel(Level.ERROR);
    }

    @PostMapping(value = "/createComment")
    public CustomResponseMessage createNewComment(@RequestBody Comment newComment){
        commentService.insert(newComment);
        loggy.info("Inserted a new comment into the database");
        return new CustomResponseMessage("success");
    }

    public CommentController() {
    }
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    public CommentService getCommentService() {
        return commentService;
    }


    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
