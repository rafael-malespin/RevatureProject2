package com.controller;

import com.model.CustomResponseMessage;
import com.model.Like;
import com.service.LikeService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200",  allowCredentials = "true")
@RequestMapping("/likes")
public class LikeController {
    private LikeService likeService;

    final static Logger loggy = Logger.getLogger("primaryLogger");
    static {
        loggy.setLevel(Level.ALL);
        //loggy.setLevel(Level.ERROR);
    }
    /**
     * retrieves an List of all Like object from the SERVICE layer
     * @return An ArrayList of Like objects
     */
    @GetMapping(value = "/getAllLikes")
    public List<Like> getAllLikes(){
        loggy.info("Retrieving all Likes from the Service layer/database");
        return likeService.selectAll();
    }

    /**
     * Retrieves all Like Objects associated with a particular Post object's
     * from the SERVICE layer
     * @param postId the Id number for a particular Post object
     * @return An ArrayList of Like Objects
     */
    @GetMapping(value = "/getLikesForPost/{postId}")
    public List<Like> getLikesForPost(@PathVariable("postId") int postId){
        loggy.info("Retrieving all Likes from the Service layer/database that are connected to the post with id: "+postId);
        return likeService.selectAllForPostId(postId);
    }

    /**
     * Retrieves the number of Like Objects associated with a particular Post object by
     * returning the size of an ArrayList containing the Like Objects retrieved from the
     * SERVICE layer
     * @param postId the Id number for a particular Post object
     * @return An int value
     */
    @GetMapping(value = "/getLikesForPostSize/{postId}")
    public int getLikeCountForPost(@PathVariable("postId") int postId){
        loggy.info("Retrieving the number of Likes from the Service layer/in the database connected to the post with id: "+postId);
        return likeService.selectAllForPostId(postId).size();
    }

    /**
     * Retrieves all Like Objects associated with a particular User object
     * from the SERVICE layer
     * @param userId the Id number for a particular User object
     * @return An ArrayList of Like Objects
     */
    @GetMapping(value = "/getLikesGivenByUser/{userId}")
    public List<Like> getLikesByUser(@PathVariable("userId") int userId){
        loggy.info("Retrieving all Likes from the Service layer/in the database created by the user with id: "+userId);
        return likeService.selectAllForUserId(userId);
    }

    /**
     * Inserts a new Like into the database by passing it to the SERVICE layer
     * @param like the new Like object to be inserted to the database
     * @return a confirmation message that the Like was inserted
     */
    @PostMapping(value = "/insertNewLike")
    public CustomResponseMessage insertNewLike(@RequestBody Like like){
        likeService.insert(like);
        loggy.info("Inserting a new Like into the database");
        return new CustomResponseMessage("Like was added.");
    }

    /**
     * Updates a Like object that was already in the database by calling the appropriate
     * method in the SERVICE layer
     * @param like the UPDATED Like object
     * @return a String that contains a confirmation message
     */
    @PutMapping(value = "/updateLike")
    public CustomResponseMessage updateLike(@RequestBody Like like){
        likeService.update(like);
        loggy.info("Updating a Like in the database");
        return new CustomResponseMessage("Like was updated.");
    }

    /**
     * Deletes a Like object by passing its information to the appropriate method
     * in the SERVICE layer
     * @param like the Like object to be deleted from the database
     * @return A String containing a confirmation message
     */
    @PostMapping(value = "/deleteLike")
    public CustomResponseMessage deleteLike(@RequestBody Like like){
        likeService.delete(like);
        loggy.info("Deleting a Like in the database with id: "+like.getLikeId());
        return new CustomResponseMessage("Like was deleted.");
    }

    public LikeController() {
    }

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    public LikeService getLikeService() {
        return likeService;
    }

    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }
}
