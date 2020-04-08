package org.netcracker.students.servlets;

import org.netcracker.students.dao.interfaces.DAOManager;
import org.netcracker.students.dao.postrgresql.PostgreSQLDAOManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/start")
public class StartPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = new File(ServletConstants.PATH_TO_SCRIPT_SQL);
        PostgreSQLDAOManager.getInstance(file.getAbsolutePath());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START);
        requestDispatcher.forward(req, resp);
    }
}
