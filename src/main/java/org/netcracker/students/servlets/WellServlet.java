package org.netcracker.students.servlets;

import org.netcracker.students.controller.UserController;
import org.netcracker.students.controller.utils.hashing.HashingClass;
import org.netcracker.students.controller.utils.hashing.exceptions.GeneratePasswordException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.userDAO.CreateUserException;
import org.netcracker.students.factories.UserFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;

@WebServlet("/well")
public class WellServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_WELL);
        String login = req.getParameter(ServletConstants.PARAMETER_LOGIN);
        String password = req.getParameter(ServletConstants.PARAMETER_PASSWORD);
        LocalDateTime dateOfRegistration = LocalDateTime.now();
        UserController userController = null;
        try {
            userController = UserController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        try {
            if (userController != null) {
                String hashPassword = HashingClass.generatePasswordHash(password);
                userController.addUser(UserFactory.createUser(login, hashPassword, dateOfRegistration));
            }
        } catch (CreateUserException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_USER);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_SIGN_UP).forward(req, resp);

        } catch (GeneratePasswordException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        requestDispatcher.forward(req, resp);
    }
}
