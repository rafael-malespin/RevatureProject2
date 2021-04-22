package com.service;

import com.controller.UserController;
import com.dao.UserDao;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})
@WebAppConfiguration()
public class UserServiceImplTest {

    @Mock
    UserDao userDao;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    @Spy
    private UserService userService;

    private MockMvc mockMvc;
    private List<User> users;
    private AutoCloseable item;


    @BeforeEach
    void setUp() {
        item= MockitoAnnotations.openMocks(this);

        userService.setUserDao(userDao);
        users=new ArrayList<>();
        users.add(new User(1,"rafael","max","rafael_max@yahoo.com","",
                "I like dogs",new ArrayList<Post>(),new ArrayList<Like>(),"Rafael","Maximilian"));
        users.add(new User(2,"dani3","fan122","ghost_girl@yahoo.com","",
                "I like ghost dogs",new ArrayList<Post>(),new ArrayList<Like>(),"Danielle","Fantoni"));
        users.add(new User(3,"dani32","fan1222","ghost_girl2@yahoo.com","",
                "I like ghost dogs too",new ArrayList<Post>(),new ArrayList<Like>(),"Danielle","Fantoni"));

    }

    @AfterEach
    void tearDown() throws Exception {
        item.close();

        users=null;
    }

    @Test
    void insert() {
        User user = Mockito.spy(users.get(1));
        Mockito.doNothing().when(userDao).insert(user);
        userService.insert(user);
        Assertions.assertNotEquals("fan122",user.getPassword());
        Mockito.verify(user,Mockito.times(2)).getPassword();
        Mockito.verify(userDao).insert(user);
        Mockito.verify(userService).hashPassword("fan122");
    }

    @Test
    void update() {
        User user = Mockito.spy(users.get(1));
        Mockito.doNothing().when(userDao).update(user);
        userService.update(user);
        Mockito.verify(userService).update(user);
    }

    @Test
    void updateWithPassword() {
        User user = Mockito.spy(users.get(1));
        Mockito.doNothing().when(userDao).update(user);
        userService.updateWithPassword(user);

        Mockito.verify(user,Mockito.times(1)).getPassword();
        Mockito.verify(userDao).update(user);
        Mockito.verify(userService).hashPassword("fan122");
        Assertions.assertNotEquals("fan122",user.getPassword());
    }

    @Test
    void delete() {
        User user = Mockito.spy(users.get(2));
        Mockito.doNothing().when(userDao).delete(user);
        userService.delete(user);
        Mockito.verify(userService).delete(user);
    }

    @Test
    void selectByUsernameAndPassword() {
        User user = Mockito.spy(users.get(2));
        Mockito.doReturn(user).when(userDao).selectByUsernameAndPassword("dani32","fan1222");
        Mockito.doReturn("fan1222").when(userService).hashPassword("fan1222");
        User rUser=userService.selectByUsernameAndPassword("dani32","fan1222");
        Mockito.verify(userService).hashPassword("fan1222");
        Mockito.verify(userDao).selectByUsernameAndPassword("dani32","fan1222");
        Assertions.assertAll(
                ()->assertEquals(3,rUser.getUserId()),
                ()->assertEquals("dani32",rUser.getUsername()),
                ()->assertEquals("fan1222",rUser.getPassword()),
                ()->assertEquals("ghost_girl2@yahoo.com",rUser.getEmailAddress()),
                ()->assertEquals("I like ghost dogs too",rUser.getDescription()),
                ()->assertEquals("Danielle",rUser.getFirstName()),
                ()->assertEquals("Fantoni",rUser.getLastName()),
                ()->assertEquals(0,rUser.getPosts().size()),
                ()->assertEquals(0,rUser.getLikedPosts().size())
        );

    }

    @Test
    void selectByUsername() {
        User user = Mockito.spy(users.get(2));
        Mockito.doReturn(user).when(userDao).selectByUsername("dani32");

        User rUser=userService.selectByUsername("dani32");
        Mockito.verify(userDao).selectByUsername("dani32");
        Assertions.assertAll(
                ()->assertEquals(3,rUser.getUserId()),
                ()->assertEquals("dani32",rUser.getUsername()),
                ()->assertEquals("fan1222",rUser.getPassword()),
                ()->assertEquals("ghost_girl2@yahoo.com",rUser.getEmailAddress()),
                ()->assertEquals("I like ghost dogs too",rUser.getDescription()),
                ()->assertEquals("Danielle",rUser.getFirstName()),
                ()->assertEquals("Fantoni",rUser.getLastName()),
                ()->assertEquals(0,rUser.getPosts().size()),
                ()->assertEquals(0,rUser.getLikedPosts().size())
        );
    }

    @Test
    void checkOldPassword() {
        User user = Mockito.spy(users.get(2));
        Mockito.doReturn(user).when(userDao).selectByUsername("dani32");
        Mockito.doReturn("fan1222").when(userService).hashPassword("fan1222");
        boolean rUser=userService.checkOldPassword("dani32","fan1222");
        assertTrue(rUser);

        Mockito.doReturn("notFan1222").when(userService).hashPassword("fan1222");
        rUser=userService.checkOldPassword("dani32","fan1222");
        assertFalse(rUser);
    }

    @Test
    void selectById() {
        User user = Mockito.spy(users.get(2));
        Mockito.doReturn(user).when(userDao).selectById(3);

        User rUser=userService.selectById(3);
        Mockito.verify(userDao).selectById(3);
        Assertions.assertAll(
                ()->assertEquals(3,rUser.getUserId()),
                ()->assertEquals("dani32",rUser.getUsername()),
                ()->assertEquals("fan1222",rUser.getPassword()),
                ()->assertEquals("ghost_girl2@yahoo.com",rUser.getEmailAddress()),
                ()->assertEquals("I like ghost dogs too",rUser.getDescription()),
                ()->assertEquals("Danielle",rUser.getFirstName()),
                ()->assertEquals("Fantoni",rUser.getLastName()),
                ()->assertEquals(0,rUser.getPosts().size()),
                ()->assertEquals(0,rUser.getLikedPosts().size())
        );
    }

    @Test
    void selectAllUsers() {
        Mockito.doReturn(users).when(userDao).selectAllUsers();
        List<User> userList=userService.selectAllUsers();
        Assertions.assertEquals(3,userList.size());
    }
}