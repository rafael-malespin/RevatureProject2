package com.controller;

import com.model.CustomResponseMessage;
import com.model.Like;
import com.model.Post;
import com.model.User;
import com.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})
@WebAppConfiguration()
class PostControllerTest {

    @Autowired
    @Spy
    private PostController postController;

    @Mock
    private PostService postService;

    private AutoCloseable item;
    private List<Post> posts;
    private List<Like> likeList;
    private User user;
    @BeforeEach
    void setUp() {
        item = MockitoAnnotations.openMocks(this);
        postController.setPostService(postService);
        user = new User(2,"dani3","fan122","ghost_girl@yahoo.com","",
                "I like ghost dogs",new ArrayList<Post>(),new ArrayList<Like>(),"Danielle","Fantoni");
        likeList=new ArrayList<>();
        likeList.add(new Like(1,user,new Post()));
        posts=new ArrayList<>();
        posts.add(new Post(1, user,"New shirt is awesome","I love this new shirt I got.",
                "ghostShirt.com","youtube.com/0","1pm",new ArrayList<Like>(), new ArrayList<>()));
        posts.add(new Post(2, user,"Smoothies","I love the new Vera Berry Explosion Flavor",
                "smoothie.com","youtube.com/1","3pm",likeList , new ArrayList<>()));
        posts.add(new Post(3, user,"Skateboarding","Just did a seven eighty!!!!!",
                "skating.com/1","youtube.com/2","6pm",likeList, new ArrayList<>()));

    }

    @AfterEach
    void tearDown() throws Exception {
        item.close();
        posts=null;
    }

    @Test
    void getAllPosts() {
        Mockito.doReturn(posts).when(postService).selectAllPosts();
        List<Post> postList =  postController.getAllPosts();
        Assertions.assertEquals(3,postList.size());
    }

    @Test
    void getPostById() {
        Mockito.doReturn(posts.get(0)).when(postService).selectById(1);
        Post rPost = postController.getPostById(1);
        Assertions.assertAll(
                ()->assertEquals(1,rPost.getPostId()),
                ()->assertEquals(user,rPost.getCreator()),
                ()->assertEquals("I love this new shirt I got.",rPost.getPostContent()),
                ()->assertEquals("New shirt is awesome",rPost.getPostTitle()),
                ()->assertEquals("ghostShirt.com",rPost.getPostImageUrl()),
                ()->assertEquals("youtube.com/0",rPost.getYoutubeUrl()),
                ()->assertEquals("1pm",rPost.getPostedAt()),
                ()->assertEquals(new ArrayList<>(),rPost.getUsersWhoLiked()),
                ()->assertEquals(new ArrayList<>(),rPost.getComments())
                );
        Mockito.doReturn(posts.get(1)).when(postService).selectById(2);
        Post rPost2 = postController.getPostById(2);
        Assertions.assertAll(
                ()->assertEquals(2,rPost2.getPostId()),
                ()->assertEquals(user,rPost2.getCreator()),
                ()->assertEquals("I love the new Vera Berry Explosion Flavor",rPost2.getPostContent()),
                ()->assertEquals("Smoothies",rPost2.getPostTitle()),
                ()->assertEquals("smoothie.com",rPost2.getPostImageUrl()),
                ()->assertEquals("youtube.com/1",rPost2.getYoutubeUrl()),
                ()->assertEquals("3pm",rPost2.getPostedAt()),
                ()->assertEquals(likeList,rPost2.getUsersWhoLiked())
        );
    }

    @Test
    void getPostsCreatedByUser() {
        Mockito.doReturn(posts).when(postService).selectAllByUser(1);
        List<Post> postList = postController.getPostsCreatedByUser(1);
        Assertions.assertEquals(3,postList.size());
    }

    @Test
    void getPostsLikedByUser() {
        List<Post> postLiked=new ArrayList<>();
        postLiked.add(posts.get(1));
        postLiked.add(posts.get(2));
        Mockito.doReturn(postLiked).when(postService).selectAllLikedByUser(1);
        List<Post> postList = postController.getPostsLikedByUser(1);
        Assertions.assertEquals(2,postList.size());
    }

    @Test
    void createNewPost() {
        Mockito.doNothing().when(postService).insert(posts.get(2));
        CustomResponseMessage message=postController.createNewPost(posts.get(2));
        Assertions.assertEquals("success",message.getMessage());
    }

    @Test
    void updatePost() {
        Mockito.doNothing().when(postService).update(posts.get(2));
        CustomResponseMessage message=postController.updatePost(posts.get(2));
        Assertions.assertEquals("update success",message.getMessage());
    }

    @Test
    void deletePost() {
        Mockito.doNothing().when(postService).delete(posts.get(2));
        CustomResponseMessage message=postController.deletePost(posts.get(2));
        Assertions.assertEquals("deleted post",message.getMessage());
    }
}