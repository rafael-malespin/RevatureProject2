package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name ="comments")
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int commentId;

    @Column(name = "comment_content")
    private String commentContent;
    @Column(name = "commented_at")
    private String commentedAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User commentWriter;

    @ManyToOne
    @JsonBackReference("postComments")
    @JoinColumn(name="post_id")
    private Post commentPost;


    public Comment() {
    }

    public Comment(int commentId, String commentContent, String commentedAt, User commentWriter, Post commentPost) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentedAt = commentedAt;
        this.commentWriter = commentWriter;
        this.commentPost = commentPost;
    }

    public Comment(String commentContent, String commentedAt, User commentWriter, Post commentPost) {
        this.commentContent = commentContent;
        this.commentedAt = commentedAt;
        this.commentWriter = commentWriter;
        this.commentPost = commentPost;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(String commentedAt) {
        this.commentedAt = commentedAt;
    }

    public User getCommentWriter() {
        return commentWriter;
    }

    public void setCommentWriter(User commentWriter) {
        this.commentWriter = commentWriter;
    }

    public Post getCommentPost() {
        return commentPost;
    }

    public void setCommentPost(Post commentPost) {
        this.commentPost = commentPost;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentContent='" + commentContent + '\'' +
                ", commentedAt='" + commentedAt + '\'' +
                ", commentWriter=" + commentWriter +
                ", commentPost=" + commentPost +
                '}';
    }
}
