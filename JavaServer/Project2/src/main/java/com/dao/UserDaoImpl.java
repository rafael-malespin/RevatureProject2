package com.dao;

import com.model.Post;
import com.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao{
    private SessionFactory sesFact;
    /**
     * CRUD method (Create) - Inserts User object into database.
     * @param user User object initiated by user.
     */
    @Override
    public void insert(User user) {
        sesFact.getCurrentSession().save(user);
    }
    /**
     * CRUD method (Update) - Updates User record in database with new User object.
     * @param user updated User object.
     */
    @Override
    public void update(User user) {
        sesFact.getCurrentSession().update(user);
    }
    /**
     * CRUD method (Delete) - Removes User record in database.
     * @param user User object to be removed.
     */
    @Override
    public void delete(User user) {
        sesFact.getCurrentSession().delete(user);
    }
    /**
     * CRUD method (Read) - Pulls User record from database based on the ID and maps to a User object in Java.
     * @param id Unique identifier of User object (also Primary Key in DB).
     * @return User object based on selected record from database.
     */
    @Override
    public User selectById(int id) {
        return sesFact.getCurrentSession().get(User.class,id);
    }

    /**
     * CRUD method (Read) - Pulls User record from database based on username and password and maps to a
     * User object in Java.
     * @param username User object field (String) - identifies username or handle of User.
     * @param password User object field (String) - identifies string used for access by User.
     * @return User object based on selected record from database.
     */
    @Override
    public User selectByUsernameAndPassword(String username, String password) {
        List<User> userList = sesFact.getCurrentSession().createQuery("from User where user_name=" +
                "'"+username+"' AND pass_word='"+password+"'",User.class).list();
        if(userList.size()==0){
            return null;
        }
        return userList.get(0);
    }

    /**
     * CRUD method (Read) - Pulls User record from database based on username and maps to a User object
     * in Java
     * @param username User object field (String) - identifies username or handle of User.
     * @return User object based on selected record from database.
     */
    @Override
    public User selectByUsername(String username) {
        List<User> userList = sesFact.getCurrentSession().createQuery("from User where user_name=" +
                "'"+username+"'", User.class).list();
        if(userList.size()==0){
            return null;
        }
        return userList.get(0);
    }
    /**
     * CRUD method (Read) - Pulls all User records from database and maps to Java list of User objects.
     * @return Array list of User objects.
     */
    @Override
    public List<User> selectAllUsers() {
        return sesFact.getCurrentSession().createQuery("from User",User.class).list();
    }

    public UserDaoImpl() {
    }

    public UserDaoImpl(SessionFactory sesFact) {
        this.sesFact = sesFact;
    }

    public SessionFactory getSesFact() {
        return sesFact;
    }

    @Autowired
    public void setSesFact(SessionFactory sesFact) {
        this.sesFact = sesFact;
    }
}
