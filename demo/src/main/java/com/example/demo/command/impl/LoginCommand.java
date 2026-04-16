package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.sun.jdi.connect.spi.Connection;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        UserService userService = UserServiceImpl.getInstance();
        String page;
        if (userService.authenticate(login, password)) {
            logger.info("Correct login");
            request.setAttribute("user", login);
            page = "pages/main.jsp";
        }   else {
            request.setAttribute("login_msg", "incorrect login or pass");
            page = "index.jsp";
        }

        return page;
    }
}
