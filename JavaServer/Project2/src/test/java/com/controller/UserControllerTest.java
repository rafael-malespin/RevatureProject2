package com.controller;

import com.model.CustomResponseMessage;
import com.model.Like;
import com.model.Post;
import com.model.User;
import com.service.UserService;
import com.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})
@WebAppConfiguration()
public class UserControllerTest {


    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private AutoCloseable item;
    private List<User> users;

    @BeforeEach
    public void setup() {
        //this.mockMvc = MockMvcBuilders.standaloneSetup()
        item=MockitoAnnotations.openMocks(this);
        userController.setUserService(userService);
        //mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
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
    void getAllUsers() {
        Mockito.doReturn(users).when(userService).selectAllUsers();
        List<User> userList = userController.getAllUsers();
        Assertions.assertEquals(3,userList.size(),"Testing if getAllUsers gets back two users");
    }

    @Test
    void getUserById() {
        Mockito.doReturn(users.get(0)).when(userService).selectById(1);
        User rUser=userController.getUserById(1);
        Assertions.assertAll(
                ()->assertEquals(1,rUser.getUserId()),
                ()->assertEquals("rafael",rUser.getUsername()),
                ()->assertEquals("max",rUser.getPassword()),
                ()->assertEquals("rafael_max@yahoo.com",rUser.getEmailAddress()),
                ()->assertEquals("I like dogs",rUser.getDescription()),
                ()->assertEquals("Rafael",rUser.getFirstName()),
                ()->assertEquals("Maximilian",rUser.getLastName()),
                ()->assertEquals(0,rUser.getPosts().size()),
                ()->assertEquals(0,rUser.getLikedPosts().size())
        );
    }

    @Test
    void getUserByUserAndPass() {
        Mockito.doReturn(users.get(0)).when(userService).selectByUsernameAndPassword("rafael","max");
        User user = new User();
        user.setUsername("rafael");
        user.setPassword("max");
        User rUser=userController.getUserByUserAndPass(user);
        Assertions.assertAll(
                ()->assertEquals(1,rUser.getUserId()),
                ()->assertEquals("rafael",rUser.getUsername()),
                ()->assertEquals("max",rUser.getPassword()),
                ()->assertEquals("rafael_max@yahoo.com",rUser.getEmailAddress()),
                ()->assertEquals("I like dogs",rUser.getDescription()),
                ()->assertEquals("Rafael",rUser.getFirstName()),
                ()->assertEquals("Maximilian",rUser.getLastName()),
                ()->assertEquals(0,rUser.getPosts().size()),
                ()->assertEquals(0,rUser.getLikedPosts().size())
        );
    }

    @Test
    void getUserByUsername() {
        Mockito.doReturn(users.get(0)).when(userService).selectByUsername("rafael");
        User user = new User();
        user.setUsername("rafael");
        user.setPassword("max");
        User rUser=userController.getUserByUsername(user);
        Assertions.assertAll(
                ()->assertEquals(1,rUser.getUserId()),
                ()->assertEquals("rafael",rUser.getUsername()),
                ()->assertEquals("max",rUser.getPassword()),
                ()->assertEquals("rafael_max@yahoo.com",rUser.getEmailAddress()),
                ()->assertEquals("I like dogs",rUser.getDescription()),
                ()->assertEquals("Rafael",rUser.getFirstName()),
                ()->assertEquals("Maximilian",rUser.getLastName()),
                ()->assertEquals(0,rUser.getPosts().size()),
                ()->assertEquals(0,rUser.getLikedPosts().size())
        );
    }

    @Test
    void insertNewUserSuccess() {
        User user = new User();
        user.setUsername("jorge");
        user.setPassword("bax");
        Mockito.doReturn(null).when(userService).selectByUsername("jorge");
        Mockito.doNothing().when(userService).insert(user);
        CustomResponseMessage message = userController.insertNewUser(user);
        assertEquals("User was created",message.getMessage());
    }
    @Test
    void insertNewUserFail() {
        User user = new User();
        user.setUsername("rafael");
        user.setPassword("max");
        Mockito.doReturn(user).when(userService).selectByUsername("rafael");
        Mockito.doNothing().when(userService).insert(user);
        CustomResponseMessage message = userController.insertNewUser(user);
        assertEquals("Username already taken",message.getMessage());
    }

    @Test
    void updateUser() {
        User user = new User(2,"dani3","fan122","ghost_girl@yahoo.com","",
                "I like ghost dogs",new ArrayList<Post>(),new ArrayList<Like>(),"Danielle",
                "Fantoni");
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.doReturn(users.get(1)).when(session).getAttribute("loggedInUser");
        Mockito.doNothing().when(userService).updateWithPassword(user);
        Mockito.doNothing().when(userService).update(user);
        Mockito.doReturn(user).when(userService).selectById(user.getUserId());
        CustomResponseMessage message = userController.updateUser(session,user);
        assertEquals("User was updated",message.getMessage());
        Mockito.verify(userService,Mockito.times(1)).update(user);
        Mockito.verify(userService,Mockito.times(0)).updateWithPassword(user);
        //change password
        user.setPassword("apple");
        System.out.println(user);
        message = userController.updateUser(session,user);
        assertEquals("User was updated",message.getMessage());
        Mockito.verify(userService,Mockito.times(1)).update(user);
        Mockito.verify(userService,Mockito.times(1)).updateWithPassword(user);

    }
    @Test
    void updateUserFail(){
        User user = new User(2,"dani3","fan122","ghost_girl@yahoo.com","",
                "I like ghost dogs",new ArrayList<Post>(),new ArrayList<Like>(),"Danielle","Fantoni");
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.doReturn(null).when(session).getAttribute("loggedInUser");
        Mockito.doNothing().when(userService).updateWithPassword(user);
        Mockito.doNothing().when(userService).update(user);
        Mockito.doReturn(user).when(userService).selectById(user.getUserId());
        CustomResponseMessage message = userController.updateUser(session,user);
        assertEquals("User was not updated",message.getMessage());
        Mockito.verify(userService,Mockito.times(0)).update(user);
        Mockito.verify(userService,Mockito.times(0)).updateWithPassword(user);

    }

    @Test
    void deleteUser() {
        User user = users.get(1);
        Mockito.doNothing().when(userService).delete(user);
        CustomResponseMessage message = userController.deleteUser(user);
        assertEquals("User was deleted",message.getMessage());
    }

    @Test
    void getLoggedInUser() {
        User user = users.get(0);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.doReturn(user).when(session).getAttribute("loggedInUser");
        Mockito.doReturn(user).when(userService).selectById(1);

        User rUser = userController.getLoggedInUser(session);
        Assertions.assertAll(
                ()->assertEquals(1,rUser.getUserId()),
                ()->assertEquals("rafael",rUser.getUsername()),
                ()->assertEquals("max",rUser.getPassword()),
                ()->assertEquals("rafael_max@yahoo.com",rUser.getEmailAddress()),
                ()->assertEquals("I like dogs",rUser.getDescription()),
                ()->assertEquals("Rafael",rUser.getFirstName()),
                ()->assertEquals("Maximilian",rUser.getLastName()),
                ()->assertEquals(0,rUser.getPosts().size()),
                ()->assertEquals(0,rUser.getLikedPosts().size())
                //()->assertEquals(0,rUser.getComments().size())
        );
        Mockito.verify(session,Mockito.times(1)).setAttribute("loggedInUser",user);
    }
    @Test
    void getLoggedInUserFail() {
        User user = users.get(0);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.doReturn(null).when(session).getAttribute("loggedInUser");
        Mockito.doReturn(user).when(userService).selectById(1);

        User rUser = userController.getLoggedInUser(session);
        assertNull(rUser);

    }

    @Test
    void login() {
        User user = users.get(0);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.doReturn(user).when(userService).selectByUsernameAndPassword(user.getUsername(),user.getPassword());
        User rUser = userController.login(session,user);
        Assertions.assertAll(
                ()->assertEquals(1,rUser.getUserId()),
                ()->assertEquals("rafael",rUser.getUsername()),
                ()->assertEquals("max",rUser.getPassword()),
                ()->assertEquals("rafael_max@yahoo.com",rUser.getEmailAddress()),
                ()->assertEquals("I like dogs",rUser.getDescription()),
                ()->assertEquals("Rafael",rUser.getFirstName()),
                ()->assertEquals("Maximilian",rUser.getLastName()),
                ()->assertEquals(0,rUser.getPosts().size()),
                ()->assertEquals(0,rUser.getLikedPosts().size())
        );
        Mockito.doReturn(null).when(userService).selectByUsernameAndPassword(user.getUsername(),user.getPassword());
        User rUser2 = userController.login(session,user);
        assertNull(rUser2);
    }

    @Test
    void logout() {
        User user = users.get(0);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession mySession=Mockito.mock(HttpSession.class);
        Mockito.doReturn(mySession).when(request).getSession();
        CustomResponseMessage message = userController.logout(request);
        assertEquals("Logged out",message.getMessage());
    }
}
