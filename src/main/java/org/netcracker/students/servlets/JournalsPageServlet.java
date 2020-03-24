package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.xml.XMLParser;
import org.netcracker.students.model.Journal;
import org.netcracker.students.controller.utils.xml.Journals;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/journals")
public class JournalsPageServlet extends HttpServlet {
    public static final String PATH_TO_VIEW = "view/journals.jsp";
    public static final String ATTRIBUTE_NAME = "journals";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(PATH_TO_VIEW);
        JournalsController journalsController = JournalsController.getInstance();
        List<Journal> journalArrayList = journalsController.getAll();
        if (journalArrayList.isEmpty()) {
            req.setAttribute(ATTRIBUTE_NAME, null);
        } else {
            XMLParser xmlParser = XMLParser.getInstance();
            String allJournals = xmlParser.toXML(new Journals(journalArrayList));
            req.setAttribute(ATTRIBUTE_NAME, allJournals);
        }
        requestDispatcher.forward(req, resp);
    }
}
