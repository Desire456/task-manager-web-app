package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.xml.Journals;
import org.netcracker.students.controller.utils.xml.XMLParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteJournal")
public class DeleteJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JournalsController journalsController = JournalsController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        String ids = req.getParameter(ServletConstants.PARAMETER_IDS);
        journalsController.deleteJournal(ids);
        String allJournals = xmlParser.toXML(new Journals(journalsController.getAll()));
        req.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                allJournals);
        req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE).forward(req, resp);
    }
}
