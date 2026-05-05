package com.example.demo.dao.impl;

import com.example.demo.dao.BaseDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.exception.DaoException;
import com.example.demo.pool.ConnectionPool;
import com.example.demo.util.PasswordHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static UserDaoImpl instance;

    private static final String SQL_SELECT_PASSWORD =
            "SELECT password FROM users WHERE lastname = ? AND confirmed = TRUE";
    private static final String SQL_INSERT_USER =
            "INSERT INTO users(lastname, password, email, confirmed) VALUES(?, ?, ?, FALSE)";
    private static final String SQL_INSERT_TOKEN =
            "INSERT INTO confirmation_tokens(user_id, token, expires_at) VALUES(?, ?, DATE_ADD(NOW(), INTERVAL 24 HOUR))";
    private static final String SQL_CONFIRM_TOKEN =
            "UPDATE users u "
            + "JOIN confirmation_tokens t ON u.id = t.user_id "
            + "SET u.confirmed = TRUE "
            + "WHERE t.token = ? AND t.expires_at > NOW() AND u.confirmed = FALSE";
    private static final String SQL_DELETE_TOKEN =
            "DELETE FROM confirmation_tokens WHERE token = ?";

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            synchronized (UserDaoImpl.class) {
                if (instance == null) {
                    instance = new UserDaoImpl();
                }
            }
        }
        return instance;
    }

    /** Legacy insert without email — kept for BaseDao contract; delegates to insertWithToken. */
    @Override
    public boolean insert(User user) throws DaoException {
        throw new DaoException("Use insertWithToken() to register new users");
    }

    @Override
    public boolean insertWithToken(User user, String token) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            long userId;
            try (PreparedStatement stmtUser = connection.prepareStatement(SQL_INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS)) {
                stmtUser.setString(1, user.getLastname());
                stmtUser.setString(2, PasswordHandler.hashPassword(user.getPassword()));
                stmtUser.setString(3, user.getEmail());
                stmtUser.executeUpdate();

                try (ResultSet generatedKeys = stmtUser.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        throw new DaoException("Failed to retrieve generated user ID");
                    }
                    userId = generatedKeys.getLong(1);
                }
            }

            try (PreparedStatement stmtToken = connection.prepareStatement(SQL_INSERT_TOKEN)) {
                stmtToken.setLong(1, userId);
                stmtToken.setString(2, token);
                stmtToken.executeUpdate();
            }

            connection.commit();
            logger.info("User {} inserted with confirmation token", user.getLastname());
            return true;

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                logger.error("Rollback failed", rollbackEx);
            }
            logger.error("Failed to insert user with token", e);
            throw new DaoException("Failed to insert user with confirmation token", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Failed to restore auto-commit", e);
            }
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public boolean confirmByToken(String token) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            int updated;
            try (PreparedStatement stmtConfirm = connection.prepareStatement(SQL_CONFIRM_TOKEN)) {
                stmtConfirm.setString(1, token);
                updated = stmtConfirm.executeUpdate();
            }

            try (PreparedStatement stmtDelete = connection.prepareStatement(SQL_DELETE_TOKEN)) {
                stmtDelete.setString(1, token);
                stmtDelete.executeUpdate();
            }

            connection.commit();
            return updated > 0;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                logger.error("Rollback failed", rollbackEx);
            }
            logger.error("Failed to confirm registration by token", e);
            throw new DaoException("Failed to confirm registration", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("Failed to restore auto-commit", e);
            }
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PASSWORD)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String passFromDb = resultSet.getString(1);
                    return PasswordHandler.checkPassword(password, passFromDb);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to authenticate user {}", login, e);
            throw new DaoException("Authentication query failed", e);
        }
        return false;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return List.of();
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        return Optional.empty();
    }
}
