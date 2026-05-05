package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    private static final String PARAM_LOGIN    = "login";
    private static final String PARAM_PASS     = "pass";
    private static final String ATTR_USER      = "user";
    private static final String ATTR_LOGIN_MSG = "login_msg";
    private static final String PAGE_MAIN      = "pages/main.jsp";
    private static final String PAGE_INDEX     = "index.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String login    = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASS);
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (userService.authenticate(login, password)) {
                logger.info("User {} logged in", login);
                request.setAttribute(ATTR_USER, login);
                return PAGE_MAIN;
            } else {
                request.setAttribute(ATTR_LOGIN_MSG, "Incorrect login or password, or account not yet confirmed.");
                return PAGE_INDEX;
            }
        } catch (ServiceException e) {
            logger.error("Error during authentication for {}", login, e);
            request.setAttribute(ATTR_LOGIN_MSG, "Internal error. Please try again later.");
            return PAGE_INDEX;
        }
    }
}
