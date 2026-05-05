package com.example.demo.service.impl;

import com.example.demo.dao.impl.UserDaoImpl;
import com.example.demo.entity.User;
import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private static UserServiceImpl instance;

    private final EmailService emailService = EmailServiceImpl.getInstance();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            synchronized (UserServiceImpl.class) {
                if (instance == null) {
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean registerUser(User user) throws ServiceException {
        String token = UUID.randomUUID().toString();
        try {
            UserDaoImpl userDao = UserDaoImpl.getInstance();
            boolean inserted = userDao.insertWithToken(user, token);
            if (!inserted) {
                return false;
            }
            emailService.sendConfirmationEmail(user.getEmail(), token);
            logger.info("User {} registered, confirmation email sent to {}", user.getLastname(), user.getEmail());
            return true;
        } catch (DaoException e) {
            logger.error("DAO error during user registration for {}", user.getLastname(), e);
            throw new ServiceException("Registration failed", e);
        }
    }

    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        try {
            return UserDaoImpl.getInstance().authenticate(login, password);
        } catch (DaoException e) {
            logger.error("DAO error during authentication for user {}", login, e);
            throw new ServiceException("Authentication failed", e);
        }
    }

    @Override
    public boolean confirmRegistration(String token) throws ServiceException {
        try {
            boolean confirmed = UserDaoImpl.getInstance().confirmByToken(token);
            if (confirmed) {
                logger.info("Account confirmed via token {}", token);
            } else {
                logger.warn("Invalid or expired confirmation token: {}", token);
            }
            return confirmed;
        } catch (DaoException e) {
            logger.error("DAO error during email confirmation", e);
            throw new ServiceException("Confirmation failed", e);
        }
    }
}
