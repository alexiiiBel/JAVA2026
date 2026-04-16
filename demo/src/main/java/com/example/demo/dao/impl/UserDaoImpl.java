package com.example.demo.dao.impl;

import com.example.demo.dao.BaseDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.pool.ConnectionPool;
import com.example.demo.util.PasswordHandler;

import java.sql.*;
import java.util.List;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static UserDaoImpl instance = new UserDaoImpl();
    private static final String SELECT_LOGIN_PASSWORD = "SELECT password FROM users WHERE lastname = ?";
    private static final String INSERT_USER = "INSERT INTO users(lastname, password) VALUES(?, ?)";
    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getLastname());
            statement.setString(2, PasswordHandler.hashPassword(user.getPassword()));
            int affectedRows = statement.executeUpdate();
            match = true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            match = false;
        }
        return match;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean authenticate(String login, String password) {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            String passFromDb;
            while (resultSet.next()) {
                passFromDb = resultSet.getString(1);
                match = PasswordHandler.checkPassword(password, passFromDb);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return match;
    }
}
