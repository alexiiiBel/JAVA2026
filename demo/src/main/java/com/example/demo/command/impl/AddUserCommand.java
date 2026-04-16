package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.entity.User;
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

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        User user = new User(login, password);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        String page;
        if (violations.isEmpty()) {
            UserService userService = UserServiceImpl.getInstance();
            if (userService.addUser(user)) {
                logger.info("New user {} registered", user.getLastname());
                request.setAttribute("user", login);
                page = "main.jsp";
            } else {
                request.setAttribute("login_msg", "Registration failed in database");
                page = "index.jsp";
            }
        } else {
            logger.warn("Validation failed for user: {}", login);
            request.setAttribute("validation_errors", violations);
            page = "index.jsp";
        }

        return page;
    }
}
