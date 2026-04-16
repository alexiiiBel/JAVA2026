package com.example.demo.controller;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import com.example.demo.command.Command;
import com.example.demo.command.CommandType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "controller", value = "/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String commandStr = request.getParameter("command");
        ArrayList<String> result = new ArrayList<>();
        request.setAttribute("resArr", result);
        Command command = CommandType.define(commandStr);
        String page = command.execute(request);
        request.getRequestDispatcher(page).forward(request, response);
    }

    public void destroy() {
    }
}