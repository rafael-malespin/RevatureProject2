package com.service;

import com.dao.UserDao;
import com.model.User;

import java.util.List;

public interface UserService {

    public void insert(User user);
    public void update(User user);

    void updateWithPassword(User user);

    public void delete(User user);
    public User selectByUsernameAndPassword(String username,String password);
    public User selectByUsername(String username);

    public boolean checkOldPassword(String username, String oldPassword);

    String hashPassword(String password);

    public User selectById(int id);
    public List<User> selectAllUsers();
    public UserDao getUserDao();
    public void setUserDao(UserDao dao);
}
