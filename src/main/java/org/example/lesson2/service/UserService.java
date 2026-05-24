package org.example.lesson2.service;

import org.example.lesson2.dao.UserDao;
import org.example.lesson2.dao.UserDaoImpl;
import org.example.lesson2.model.User;

import java.time.LocalDate;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDaoImpl();

    public void createUser(Integer id,
                           String name,
                           String email,
                           Integer age) {
        User user = User.builder()
                .id(id)
                .name(name)
                .email(email)
                .age(age)
                .createdAt(LocalDate.now())
                .build();

        userDao.save(user);
    }

    public User getUser(Integer id) {
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(Integer id) {
        userDao.delete(id);
    }
}
