package com.service;

import com.dao.LikeDao;
import com.model.CustomResponseMessage;
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
class LikeServiceImplTest {

    @Mock
    private LikeDao likeDao;

    @Autowired
    @Spy
    private LikeService likeService;

    private AutoCloseable item;
    private List<Like> likeList;
    private List<Post> posts;
    private List<User> users;

    @BeforeEach
    void setUp() {
        item = MockitoAnnotations.openMocks(this);
        likeService.setLikeDao(likeDao);
        users=new ArrayList<>();
        users.add(new User(1,"rafael","max","rafael_max@yahoo.com","",
                "I like dogs",new ArrayList<Post>(),new ArrayList<Like>(),"Rafael","Maximilian"));
        users.add(new User(2,"dani3","fan122","ghost_girl@yahoo.com","",
                "I like ghost dogs",new ArrayList<Post>(),new ArrayList<Like>(),"Danielle","Fantoni"));
        users.add(new User(3,"dani32","fan1222","ghost_girl2@yahoo.com","",
                "I like ghost dogs too",new ArrayList<Post>(),new ArrayList<Like>(),"Danielle","Fantoni"));

        posts=new ArrayList<>();
        posts.add(new Post(1, users.get(0),"New shirt is awesome","I love this new shirt I got.",
                "ghostShirt.com","youtube.com/0","1pm",new ArrayList<Like>(),new ArrayList<>()));
        posts.add(new Post(2, users.get(1),"Smoothies","I love the new Vera Berry Explosion Flavor",
                "smoothie.com","youtube.com/1","3pm",likeList,new ArrayList<>()));
        posts.add(new Post(3, users.get(1),"Skateboarding","Just did a seven eighty!!!!!",
                "skating.com/1","youtube.com/2","6pm",likeList,new ArrayList<>()));

        likeList=new ArrayList<>();
        likeList.add(new Like(1,users.get(1),posts.get(1)));
        likeList.add(new Like(2,users.get(0),posts.get(1)));
        likeList.add(new Like(3,users.get(2),posts.get(0)));
        likeList.add(new Like(4,users.get(0),posts.get(2)));
    }

    @AfterEach
    void tearDown() throws Exception {
        item.close();
        users=null;
    }

    @Test
    void insert() {
        Like like = Mockito.spy(likeList.get(1));
        Mockito.doNothing().when(likeDao).insert(like);
        likeService.insert(like);
        Mockito.verify(likeDao).insert(like);
    }

    @Test
    void update() {
        Like like = Mockito.spy(likeList.get(1));
        Mockito.doNothing().when(likeDao).update(like);
        likeService.update(like);
        Mockito.verify(likeDao).update(like);
    }

    @Test
    void delete() {
        Like like = Mockito.spy(likeList.get(1));
        Mockito.doNothing().when(likeDao).delete(like);
        likeService.delete(like);
        Mockito.verify(likeDao).delete(like);
    }

    @Test
    void selectAll() {
        Mockito.doReturn(likeList).when(likeDao).getAll();
        List<Like> likeList1 = likeService.selectAll();
        Assertions.assertEquals(4,likeList1.size());
    }

    @Test
    void selectAllForPostId() {
        List<Like> pLikes = new ArrayList<>();
        pLikes.add(likeList.get(0));
        pLikes.add(likeList.get(1));
        Mockito.doReturn(pLikes).when(likeDao).getAllForPost(2);
        List<Like> rLikes = likeService.selectAllForPostId(2);
        Assertions.assertEquals(2,rLikes.size());
    }

    @Test
    void selectAllForUserId() {
        List<Like> pLikes = new ArrayList<>();
        pLikes.add(likeList.get(1));
        pLikes.add(likeList.get(3));
        Mockito.doReturn(pLikes).when(likeDao).getAllForUser(0);
        List<Like> rLikes = likeService.selectAllForUserId(0);
        Assertions.assertEquals(2,rLikes.size());
    }
}