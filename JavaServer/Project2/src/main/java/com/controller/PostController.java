package com.controller;

import com.model.Comment;
import com.model.CustomResponseMessage;
import com.model.Post;
import com.service.PostService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",  allowCredentials = "true")
@RequestMapping("/posts")
public class PostController {
    private PostService postService;
    final static Logger loggy = Logger.getLogger("primaryLogger");
    static {
        loggy.setLevel(Level.ALL);
        //loggy.setLevel(Level.ERROR);
    }
    /**
     * Retrieves ALL Post objects currently in the database from the SERVICE layer
     * @return An ArrayList of Post objects
     */
    @GetMapping(value="getAllPosts")
    public List<Post> getAllPosts(){
        loggy.info("Retrieved all posts from the Service layer/database");
        List<Post> posts= postService.selectAllPosts();
        for(int i =0; i<posts.size();i++){
            Post post = posts.get(i);
            List<Comment> comments=post.getComments();
            List<Comment> newComments=new ArrayList<>();
            for(Comment comment:comments){
                if(!newComments.contains(comment)){
                    newComments.add(comment);
                }
            }
            post.setComments(newComments);
            posts.set(i,post);
        }
        return posts;
    }

    /**
     * Retrieves a particular Post object(identified with the id number) retrieved from the SERVICE layer
     * @param id The Id number of a particular Post object
     * @return A Post object
     */
    @GetMapping(value = "/getPostById/{postId}")
    public Post getPostById(@PathVariable("postId") int id){
        loggy.info("Retrieved post from the Service layer/database with the id: "+id);
        Post post = postService.selectById(id);
        List<Comment> comments=post.getComments();
        List<Comment> newComments=new ArrayList<>();
        for(Comment comment:comments){
            if(!newComments.contains(comment)){
                newComments.add(comment);
            }
        }
        post.setComments(newComments);

        return post;
    }

    /**
     * Retrieves ALL Post objects created by a particular User object from the SERVICE layer by calling the respective
     * method and passing the userId
     * @param userid The Id associated with User object
     * @return An ArrayList of Post objects
     */
    @GetMapping(value = "/getPostsByUser/{userId}")
    public List<Post> getPostsCreatedByUser(@PathVariable("userId") int userid){
        loggy.info("Retrieved all posts from the Service layer/database created by the User with id: "+userid);
        List<Post> posts=postService.selectAllByUser(userid);
        for(int i =0; i<posts.size();i++){
            Post post = posts.get(i);
            List<Comment> comments=post.getComments();
            List<Comment> newComments=new ArrayList<>();
            for(Comment comment:comments){
                if(!newComments.contains(comment)){
                    newComments.add(comment);
                }
            }
            post.setComments(newComments);
            posts.set(i,post);
        }
        return posts;
    }

    /**
     * Retrieves ALL the Post objects that has Like object connect it to a particular User object from the SERVICE layer
     * @param userid The ID associated with a User object
     * @return An ArrayList of Post objects
     */
    @GetMapping(value = "/getPostsLikedByUser/{userId}")
    public List<Post> getPostsLikedByUser(@PathVariable("userId") int userid){
        loggy.info("Retrieved all posts from the Service layer/database liked by the User with id: "+userid);
        List<Post> posts=postService.selectAllLikedByUser(userid);
        for(int i =0; i<posts.size();i++){
            Post post = posts.get(i);
            List<Comment> comments=post.getComments();
            List<Comment> newComments=new ArrayList<>();
            for(Comment comment:comments){
                if(!newComments.contains(comment)){
                    newComments.add(comment);
                }
            }
            post.setComments(newComments);
            posts.set(i,post);
        }
        return posts;
    }

    /**
     * Inserts a new Post by passing its information to the appropriate method in the SERVICE layer
     * @param newPost The new Post object
     * @return String containing confirmation message
     */
    @PostMapping(value = "/createPost")
    public CustomResponseMessage createNewPost(@RequestBody Post newPost){
        postService.insert(newPost);
        loggy.info("Inserted a new post to the database.");
        return new CustomResponseMessage("success");
    }
    /**
     * Updates a Post by passing its new information to the appropriate method in the SERVICE layer
     * @param updatedPost The updated Post object
     * @return String containing confirmation message
     */
    @PutMapping(value = "/updatePost")
    public CustomResponseMessage updatePost(@RequestBody Post updatedPost){
        postService.update(updatedPost);
        loggy.info("Updated a post already within the database");
        return new CustomResponseMessage("update success");
    }
    /**
     * Deletes a Post by passing its information to the appropriate method in the SERVICE layer
     * @param postToBeDeleted The Post object to be deleted
     * @return String containing confirmation message
     */
    @PostMapping(value = "/deletePost")
    public CustomResponseMessage deletePost(@RequestBody Post postToBeDeleted){
        postService.delete(postToBeDeleted);
        loggy.info("Deleted a post with id: "+postToBeDeleted.getPostId() +" from the database.");
        return new CustomResponseMessage("deleted post");
    }


    public PostController() {
    }

    @Autowired
    public PostController(PostService postService) {

        this.postService = postService;
    }

    public PostService getPostService() {
        return postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }
}
