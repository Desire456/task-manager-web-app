package org.netcracker.students.servlets;


import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.Journals;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exception.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exception.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exception.journalDAO.UpdateJournalException;
import org.netcracker.students.factories.JournalFactory;
import org.netcracker.students.model.Journal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editJournal")
public class EditJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JournalsController journalsController = JournalsController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        int id = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_ID));
        System.out.println(id);
        JournalFactory journalFactory = new JournalFactory();
        Journal oldJournal = null;
        try {
            oldJournal = journalsController.getJournal(id);
        } catch (SQLException | ReadJournalException e) {
            e.printStackTrace();
        }
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        try {
            journalsController.changeJournal(journalFactory.createJournal(id, name, description, userId,
                    oldJournal.getCreationDate(), ServletConstants.PARAMETER_ACCESS_MODIFIER));
        } catch (SQLException | UpdateJournalException e) {
            e.printStackTrace();
        }
        String allJournals = null;
        try {
            allJournals = xmlParser.toXML(new Journals(journalsController.getAll(userId)));
        } catch (SQLException | GetAllJournalByUserIdException e) {
            e.printStackTrace();
        }
        req.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                allJournals);
        req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE).forward(req, resp);
    }
}
