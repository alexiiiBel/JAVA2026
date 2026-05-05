package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.exception.DaoException;

public interface UserDao {

    boolean authenticate(String login, String password) throws DaoException;

    boolean insertWithToken(User user, String token) throws DaoException;

    boolean confirmByToken(String token) throws DaoException;
}
