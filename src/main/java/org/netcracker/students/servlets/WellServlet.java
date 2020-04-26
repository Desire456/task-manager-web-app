package org.netcracker.students.servlets;

import org.netcracker.students.controller.UserController;
import org.netcracker.students.controller.utils.hashing.HashPasswordException;
import org.netcracker.students.controller.utils.hashing.HashingClass;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.userDAO.CreateUserException;
import org.netcracker.students.factories.UserFactory;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(MappingConstants.WELL_PAGE_MAPPING)
public class WellServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_WELL);
        String login = req.getParameter(ServletConstants.PARAMETER_LOGIN);
        String password = req.getParameter(ServletConstants.PARAMETER_PASSWORD);
        LocalDateTime dateOfRegistration = LocalDateTime.now();
        try {
            this.addUser(login, password, dateOfRegistration);
        } catch (GetConnectionException | HashPasswordException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_SIGN_UP);
        } catch (CreateUserException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_USER);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_SIGN_UP).forward(req, resp);
        }
        /*UserController userController = null;
        try {
            userController = UserController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        try {
            if (userController != null) {
                HashingClass hashingClass = HashingClass.getInstance();
                String hashPassword = hashingClass.hashPassword(password);
                userController.addUser(UserFactory.createUser(login, hashPassword, dateOfRegistration));
            }
        } catch (CreateUserException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_USER);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_SIGN_UP).forward(req, resp);
        } catch (HashPasswordException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_SIGN_UP);
        }*/
        requestDispatcher.forward(req, resp);
    }

    private void addUser(String login, String password, LocalDateTime dateOfRegistration)
            throws GetConnectionException, HashPasswordException, CreateUserException {
        UserController userController = UserController.getInstance();
        HashingClass hashingClass = HashingClass.getInstance();
        String hashPassword = hashingClass.hashPassword(password);
        userController.addUser(UserFactory.createUser(login, hashPassword, dateOfRegistration));
    }
}
