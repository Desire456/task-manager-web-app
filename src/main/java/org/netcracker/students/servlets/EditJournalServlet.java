package org.netcracker.students.servlets;


import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.utils.JournalXMLContainer;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.UpdateJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.factories.JournalFactory;
import org.netcracker.students.model.Journal;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(MappingConstants.EDIT_JOURNAL_MAPPING)
public class EditJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        int id = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_ID));
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        try {
            this.changeJournal(id, name, description, userId);
        } catch (GetConnectionException | ReadJournalException | UpdateJournalException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
            return;
        }

        String allJournalsXml;
        try {
            allJournalsXml = this.parseJournalListToXml(userId);
        } catch (GetConnectionException | ParseXMLException | GetAllJournalByUserIdException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
            return;
        }
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                allJournalsXml);
        resp.sendRedirect(MappingConstants.JOURNALS_PAGE_MAPPING);
    }

    private void changeJournal(int id, String name, String description, int userId) throws GetConnectionException,
            ReadJournalException, UpdateJournalException {
        JournalController journalController = JournalController.getInstance();
        Journal oldJournal = journalController.getJournal(id);
        journalController.editJournal(JournalFactory.createJournal(id, name, description, userId,
                oldJournal.getCreationDate(), oldJournal.getIsPrivate()));
    }

    private String parseJournalListToXml(int userId) throws GetConnectionException, GetAllJournalByUserIdException,
            ParseXMLException {
        JournalController journalController = JournalController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new JournalXMLContainer(journalController.getAll(userId)));
    }
}
