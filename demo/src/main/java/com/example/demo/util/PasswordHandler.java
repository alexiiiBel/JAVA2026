package com.example.demo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordHandler {
    private static final Logger logger = LogManager.getLogger(PasswordHandler.class);

    private PasswordHandler() {}

    public static String hashPassword(String password) throws Exception {
        if (password == null) {
            logger.error("Error while hashing password.");
            throw new Exception("Password can't be null");
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        return hashedPassword;
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        boolean match = false;

        if (password == null || hashedPassword == null) {
            return match;
        }

        try {
            match = BCrypt.checkpw(password, hashedPassword);
        } catch (Exception e) {
            logger.error("Password verification failed", e);
        }

        return match;
    }
}
