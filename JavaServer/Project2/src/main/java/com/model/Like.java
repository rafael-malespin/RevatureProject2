package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;

    @ManyToOne
    @JoinColumn(name="user_FK")
    @JsonBackReference("userLikes")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_FK")
    @JsonBackReference("postLikes")
    private Post post;

    public Like() {
    }

    @Override
    public String toString() {
        return "Like{" +
                "likeId=" + likeId +
                ", user=" + user +
                ", post=" + post +
                '}';
    }

    public Like(int likeId, User user, Post post) {
        this.likeId = likeId;
        this.user = user;
        this.post = post;
    }
    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
