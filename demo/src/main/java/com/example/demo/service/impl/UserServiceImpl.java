package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.dao.impl.UserDaoImpl;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance = new UserServiceImpl();

    public UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }


    @Override
    public boolean addUser(User user) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = userDao.insert(user);
        return match;
    }

    @Override
    public boolean authenticate(String login, String password) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = userDao.authenticate(login, password);
        return match;
    }
}
