package com.lab3.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lab3.dao.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            if (userDAO.validateUser(username, password)) {
                req.getSession().setAttribute("user", username);
                req.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Sai ten dang nhap hoac mat khau");
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }
        } catch (SQLException | RuntimeException e) {
            req.setAttribute("error", "Loi he thong: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Loi khong xac dinh: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
        }
    }
}