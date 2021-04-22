package com.service;

import com.dao.PostDao;
import com.model.Like;
import com.model.Post;
import com.model.User;
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
class PostServiceTest {
    @Mock
    PostDao postDao;

    @Autowired
    @Spy
    private PostService postService;

    private AutoCloseable item;
    private List<Post> posts;
    private List<Like> likeList;
    private User user;


    @BeforeEach
    void setUp() {
        item = MockitoAnnotations.openMocks(this);
        postService.setPostDao(postDao);
        user = new User(2,"dani3","fan122","ghost_girl@yahoo.com","",
                "I like ghost dogs",new ArrayList<Post>(),new ArrayList<Like>(),"Danielle","Fantoni");
        likeList=new ArrayList<>();
        likeList.add(new Like(1,user,new Post()));
        posts=new ArrayList<>();
        posts.add(new Post(1, user,"New shirt is awesome","I love this new shirt I got.",
                "ghostShirt.com","youtube.com/0","1pm",new ArrayList<Like>(),new ArrayList<>()));
        posts.add(new Post(2, user,"Smoothies","I love the new Vera Berry Explosion Flavor",
                "smoothie.com","youtube.com/1","3pm",likeList,new ArrayList<>()));
        posts.add(new Post(3, user,"Skateboarding","Just did a seven eighty!!!!!",
                "skating.com/1","youtube.com/2","6pm",likeList,new ArrayList<>()));

    }

    @AfterEach
    void tearDown() throws Exception {
        item.close();
        posts=null;
    }

    @Test
    void insert() {
        Post post = Mockito.spy(posts.get(1));
        Mockito.doNothing().when(postDao).insert(post);
        postService.insert(post);
        Mockito.verify(postDao).insert(post);
    }

    @Test
    void update() {
        Post post = Mockito.spy(posts.get(1));
        Mockito.doNothing().when(postDao).update(post);
        postService.update(post);
        Mockito.verify(postService).update(post);
    }

    @Test
    void delete() {
        Post post = Mockito.spy(posts.get(1));
        Mockito.doNothing().when(postDao).delete(post);
        postService.delete(post);
        Mockito.verify(postService).delete(post);
    }

    @Test
    void selectById() {
        Mockito.doReturn(posts.get(0)).when(postDao).getPostById(1);
        Post rPost = postService.selectById(1);
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
    }

    @Test
    void selectAllPosts() {
        Mockito.doReturn(posts).when(postDao).getAllPosts();
        List<Post> postList =  postService.selectAllPosts();
        Assertions.assertEquals(3,postList.size());

    }

    @Test
    void selectAllByUser() {
        Mockito.doReturn(posts).when(postDao).getAllPostsCreatedByUser(1);
        List<Post> postList = postService.selectAllByUser(1);
        Assertions.assertEquals(3,postList.size());

    }

    @Test
    void selectAllLikedByUser() {
        List<Post> postLiked=new ArrayList<>();
        postLiked.add(posts.get(1));
        postLiked.add(posts.get(2));
        Mockito.doReturn(postLiked).when(postDao).getAllLikedPostsByUser(1);
        List<Post> postList = postService.selectAllLikedByUser(1);
        Assertions.assertEquals(2,postList.size());
    }
}