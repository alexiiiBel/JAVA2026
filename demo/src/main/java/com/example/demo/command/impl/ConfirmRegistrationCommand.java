package com.example.demo.command.impl;

import com.example.demo.command.Command;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfirmRegistrationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ConfirmRegistrationCommand.class);

    private static final String PARAM_TOKEN         = "token";
    private static final String ATTR_CONFIRM_MSG    = "confirm_msg";
    private static final String PAGE_CONFIRM_RESULT = "pages/confirm_result.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String token = request.getParameter(PARAM_TOKEN);

        if (token == null || token.isBlank()) {
            logger.warn("Confirmation attempt with empty token");
            request.setAttribute(ATTR_CONFIRM_MSG, "Invalid confirmation link.");
            return PAGE_CONFIRM_RESULT;
        }

        UserService userService = UserServiceImpl.getInstance();
        try {
            boolean confirmed = userService.confirmRegistration(token);
            if (confirmed) {
                request.setAttribute(ATTR_CONFIRM_MSG, "Your email has been confirmed! You can now log in.");
            } else {
                request.setAttribute(ATTR_CONFIRM_MSG, "The confirmation link is invalid or has expired.");
            }
        } catch (ServiceException e) {
            logger.error("Error during email confirmation", e);
            request.setAttribute(ATTR_CONFIRM_MSG, "An internal error occurred. Please try again later.");
        }

        return PAGE_CONFIRM_RESULT;
    }
}
