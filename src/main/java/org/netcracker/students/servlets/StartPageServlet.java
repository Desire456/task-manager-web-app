package org.netcracker.students.servlets;

import org.netcracker.students.dao.exceptions.managerDAO.ExecuteSqlScriptException;
import org.netcracker.students.dao.hibernate.HibernateManagerDAO;
import org.netcracker.students.dao.postrgresql.PostgreSQLManagerDAO;

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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START);
        File file = new File(ServletConstants.PATH_TO_SCRIPT_SQL);
        try {
            HibernateManagerDAO.getInstance(file.getAbsolutePath());
        } catch (ExecuteSqlScriptException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        requestDispatcher.forward(req, resp);
    }
}
