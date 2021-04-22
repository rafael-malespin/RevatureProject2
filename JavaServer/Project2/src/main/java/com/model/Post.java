package com.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="posts")
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User creator;

    @Column(name = "post_title",nullable = false)
    private String postTitle;

    @Column(name = "post_content")
    private String postContent;
    @Column(name = "post_image_link")
    private String postImageUrl;

    @Column(name = "youtube_link")
    private String youtubeUrl;

    @Column(name = "posted_at")
    private String postedAt;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    @JsonManagedReference("postLikes")
    private List<Like> usersWhoLiked = new ArrayList<>();

    @OneToMany(mappedBy = "commentPost",fetch = FetchType.EAGER)
    @JsonManagedReference("postComments")
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(int postId, User creator, String postTitle, String postContent, String postImageUrl, String youtubeUrl, String postedAt, List<Like> usersWhoLiked, List<Comment> comments) {
        this.postId = postId;
        this.creator = creator;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImageUrl = postImageUrl;
        this.youtubeUrl = youtubeUrl;
        this.postedAt = postedAt;
        this.usersWhoLiked = usersWhoLiked;
        this.comments = comments;

    }
    public Post(User creator, String postTitle, String postContent, String postImageUrl, String youtubeUrl, String postedAt, List<Like> usersWhoLiked, List<Comment> comments) {
        this.creator = creator;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImageUrl = postImageUrl;
        this.youtubeUrl = youtubeUrl;
        this.postedAt = postedAt;
        this.usersWhoLiked = usersWhoLiked;
        this.comments = comments;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public List<Like> getUsersWhoLiked() {
        return usersWhoLiked;
    }

    public void setUsersWhoLiked(List<Like> usersWhoLiked) {
        this.usersWhoLiked = usersWhoLiked;
    }

    public void addToLikedPosts(Like post){
        usersWhoLiked.add(post);
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "\nPost{" +
                "postId=" + postId +
                ", creator=" + creator +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postImageUrl='" + postImageUrl + '\'' +
                ", youtubeUrl='" + youtubeUrl + '\'' +
                ", postedAt='" + postedAt + '\'' +
                ", usersWhoLiked=" + usersWhoLiked.size() +
                ", comments=" + comments.size() +
                '}';
    }
}
