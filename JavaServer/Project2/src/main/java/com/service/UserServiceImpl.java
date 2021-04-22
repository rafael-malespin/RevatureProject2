package com.service;

import com.dao.UserDao;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public UserServiceImpl() { }
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Passes User object to DAO layer to be inserted.
     * @param user User object
     */
    @Override
    public void insert(User user) {
        //todo check if the user us
        String hashPassword = hashPassword(user.getPassword());
        user.setPassword(hashPassword);
        userDao.insert(user);
    }
    /**
     * Passes User object to DAO layer to be updated.
     * @param user User object
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void updateWithPassword(User user){
        String hashPassword = hashPassword(user.getPassword());
        user.setPassword(hashPassword);
        userDao.update(user);
    }
    /**
     * Passes User object to DAO layer to be deleted.
     * @param user User object
     */
    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    /**
     * Passes two string fields to DAO layer to obtain User object.
     * @param username User's name or handle (String).
     * @param password User's password for authentication (String).
     * @return User object from DAO method.
     */
    @Override
    public User selectByUsernameAndPassword(String username, String password) {

        String hashPassword = hashPassword(password);
        User user = userDao.selectByUsernameAndPassword(username,hashPassword);
        return user;
    }

    /**
     * Passes a string field to DAO layer to obtain User object.
     * @param username User's name or handle (String).
     * @return User object from DAO method.
     */
    @Override
    public User selectByUsername(String username) {
        return userDao.selectByUsername(username);
    }

    @Override
    public boolean checkOldPassword(String username, String oldPassword) {
        User user = userDao.selectByUsername(username);
        return user.getPassword().equals(hashPassword(oldPassword));
    }

    /**
     * Passes User's password string and creates a unique hash code that can be securely stored within the database.
     * @param password User's secure string used for login (string).
     * @return Unique string hashed from original password.
     */
    @Override
    public String hashPassword(String password) {

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Passes ID to DAO layer to obtain User object.
     * @param id User's unique identifier (Int).
     * @return User object from DAO method.
     */
    @Override
    public User selectById(int id) {
        return userDao.selectById(id);
    }

    /**
     * Calls DAO layer method to pull all existing User objects.
     * @return Array list of all User objects.
     */
    @Override
    public List<User> selectAllUsers() {
        return userDao.selectAllUsers();
    }


}
