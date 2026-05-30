package org.example.lesson2.dao;

import org.example.lesson2.model.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    User findById(Integer id);

    List<User> findAll();

    void update(User user);

    void delete(Integer id);
}
