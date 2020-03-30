package org.netcracker.students.servlets;


import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.xml.Journals;
import org.netcracker.students.controller.utils.xml.XMLParser;
import org.netcracker.students.factories.JournalFactory;
import org.netcracker.students.model.Journal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editJournal")
public class EditJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JournalsController journalsController = JournalsController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        int id = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_ID));
        Journal oldJournal = journalsController.getJournal(id + 1);
        JournalFactory journalFactory = new JournalFactory();
        journalsController.changeJournal(id + 1, journalFactory.createJournal(id + 1,
                name, ServletConstants.PARAMETER_ACCESS_MODIFIER, oldJournal.getCreationDate(), description));
        String allJournals = xmlParser.toXML(new Journals(journalsController.getAll()));
        req.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                allJournals);
        req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE).forward(req, resp);
    }
}
