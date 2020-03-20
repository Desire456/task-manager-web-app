package org.netcracker.students.servlets;

import org.netcracker.students.model.Journal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;
import java.io.IOException;
import java.util.ArrayList;

public class JournalsPageServlet extends HttpServlet {
    public static final String PATH_TO_VIEW = "view/journals.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("login"));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(PATH_TO_VIEW);
        req.setAttribute("journals", "<journals>\n" +
                "    <journal>\n" +
                "        <id>0</id>\n" +
                "        <userId>0</userId>\n" +
                "        <accessModifier>private</accessModifier>\n" +
                "        <name>asd</name>\n" +
                "        <creationDate>19-03-2020 23:24:42</creationDate>\n" +
                "        <description>asd</description>\n" +
                "    </journal>\n" +
                "</journals>");
        requestDispatcher.forward(req, resp);
    }

}
