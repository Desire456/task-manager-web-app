package org.netcracker.students.servlets;


import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.xml.XMLParser;
import org.netcracker.students.model.Journal;
import org.netcracker.students.controller.utils.xml.Journals;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editJournal")
public class EditJournalServlet extends HttpServlet {
    private static final String PARAMETER_NAME_OF_JOURNAL = "name";
    private static final String PARAMETER_DESCRIPTION_OF_JOURNAL = "description";
    private static final String PARAMETER_ID_OF_JOURNAL = "id";
    private static final String ACCESS_MODIFIER = "private";
    private static final String ATTRIBUTE_NAME = "journals";
    private static final String PATH_TO_VIEW = "view/journals.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JournalsController journalsController = JournalsController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        String name = req.getParameter(PARAMETER_NAME_OF_JOURNAL);
        String description = req.getParameter(PARAMETER_DESCRIPTION_OF_JOURNAL);
        int id = Integer.parseInt(req.getParameter(PARAMETER_ID_OF_JOURNAL));
        Journal oldJournal = journalsController.getJournal(id + 1);
        journalsController.changeJournal(id + 1, new Journal(id,
                name, ACCESS_MODIFIER, oldJournal.getCreationDate(), description));
        String allJournals = xmlParser.toXML(new Journals(journalsController.getAll()));
        req.setAttribute(ATTRIBUTE_NAME,
                allJournals);
        req.getRequestDispatcher(PATH_TO_VIEW).forward(req, resp);
    }
}
