package com.example.demo.service;

import com.example.demo.exception.ServiceException;

public interface EmailService {

    void sendConfirmationEmail(String toEmail, String confirmationToken) throws ServiceException;
}
