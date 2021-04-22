package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @Column(name="user_name", unique = true,nullable = false)
    private String username;

    @Column(name = "pass_word",nullable = false)
    private String password;
    @Column(name="email_address",unique = true,nullable = false)
    private String emailAddress;
    @Column(name="profile_picture")
    private String profilePic;
    @Column(name="user_description")
    private String description;
    @Column(name="user_first_name")
    private String firstName;
    @Column(name="user_last_name")
    private String lastName;

    @OneToMany(mappedBy = "creator",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonManagedReference("userLikes")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "commentWriter")
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    public User() {
    }

    public User(int userId, String username, String password, String emailAddress, String profilePic, String description, List<Post> posts, List<Like> likedPosts, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.profilePic = profilePic;
        this.description = description;
        this.posts = posts;
        this.likes = likedPosts;
        this.firstName = firstName;
        this.lastName =lastName;

    }
    public User(String username, String password, String emailAddress, String profilePic, String description, List<Post> posts, List<Like> likedPosts, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.profilePic = profilePic;
        this.description = description;
        this.posts = posts;
        this.likes = likedPosts;
        this.firstName = firstName;
        this.lastName =lastName;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Like> getLikedPosts() {
        return likes;
    }

    public void setLikedPosts(List<Like> likedPosts) {
        this.likes = likedPosts;
    }

    public void addToLikedPosts(Like post){
        likes.add(post);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", description='" + description + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts.size() +
                ", likes=" + likes.size() +
                '}';
    }
}
