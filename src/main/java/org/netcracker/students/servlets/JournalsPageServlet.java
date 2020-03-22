package org.netcracker.students.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/journals")
public class JournalsPageServlet extends HttpServlet {
    public static final String PATH_TO_VIEW = "view/journals.jsp";
    public static final String ATTRIBUTE_NAME = "journals";
    public static final String ATTRIBUTE_VALUE =  "null";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(PATH_TO_VIEW);
        req.setAttribute(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
        requestDispatcher.forward(req, resp);
    }
}
