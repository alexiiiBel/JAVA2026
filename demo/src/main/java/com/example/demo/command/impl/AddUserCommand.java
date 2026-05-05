package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class AddUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddUserCommand.class);

    private static final String PARAM_LOGIN    = "login";
    private static final String PARAM_PASS     = "pass";
    private static final String PARAM_EMAIL    = "email";
    private static final String ATTR_LOGIN_MSG = "login_msg";
    private static final String ATTR_ERRORS    = "validation_errors";
    private static final String PAGE_INDEX     = "index.jsp";
    private static final String PAGE_PENDING   = "pages/confirm_pending.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String login    = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASS);
        String email    = request.getParameter(PARAM_EMAIL);

        User user = User.builder()
                .lastname(login)
                .password(password)
                .email(email)
                .build();

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<User>> violations = validator.validate(user);

            if (!violations.isEmpty()) {
                logger.warn("Validation failed for user: {}", login);
                request.setAttribute(ATTR_ERRORS, violations);
                return PAGE_INDEX;
            }
        }

        UserService userService = UserServiceImpl.getInstance();
        try {
            if (userService.registerUser(user)) {
                logger.info("User {} registered, awaiting email confirmation", login);
                request.setAttribute("email", email);
                return PAGE_PENDING;
            } else {
                request.setAttribute(ATTR_LOGIN_MSG, "Registration failed. Login may already be taken.");
                return PAGE_INDEX;
            }
        } catch (ServiceException e) {
            logger.error("Service error during registration for user {}", login, e);
            request.setAttribute(ATTR_LOGIN_MSG, "Internal error during registration. Please try again later.");
            return PAGE_INDEX;
        }
    }
}
