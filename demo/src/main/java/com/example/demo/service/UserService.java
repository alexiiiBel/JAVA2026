package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService
{
    boolean addUser(User user);

    boolean authenticate(String login, String password);
}
