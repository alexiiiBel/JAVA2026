package com.example.demo.dao;

public interface UserDao {
    boolean authenticate(String login, String password);
}
