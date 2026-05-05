package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;

public interface UserService {

    /**
     * Registers a new user, saves a confirmation token and sends an email.
     *
     * @return true if the user was successfully inserted and email dispatched
     */
    boolean registerUser(User user) throws ServiceException;

    boolean authenticate(String login, String password) throws ServiceException;

    /**
     * Activates the user account that matches the given token.
     *
     * @return true if the token was valid and the account is now active
     */
    boolean confirmRegistration(String token) throws ServiceException;
}
