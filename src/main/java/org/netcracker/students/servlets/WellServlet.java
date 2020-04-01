package org.netcracker.students.servlets;

import org.netcracker.students.controller.UsersController;
import org.netcracker.students.factories.UserFactory;
import org.netcracker.students.model.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/well")
public class WellServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(ServletConstants.PARAMETER_LOGIN);
        String password = req.getParameter(ServletConstants.PARAMETER_PASSWORD);
        String role = req.getParameter(ServletConstants.PARAMETER_ROLE);
        LocalDate dateOfRegistration = LocalDate.now();
        UsersController usersController = UsersController.getInstance();
        UserFactory userFactory = new UserFactory();
        try {
            usersController.addUser(userFactory.createUser(login, password, role, dateOfRegistration));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_WELL);
        requestDispatcher.forward(req, resp);
    }
}
