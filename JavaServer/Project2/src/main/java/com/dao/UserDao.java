package com.dao;

import com.model.User;

import java.util.List;

public interface UserDao {
    public void insert(User user);
    public void update(User user);
    public void delete(User user);
    public User selectById(int id);
    public User selectByUsernameAndPassword(String username,String password);
    public List<User> selectAllUsers();
    public User selectByUsername(String username);
}
