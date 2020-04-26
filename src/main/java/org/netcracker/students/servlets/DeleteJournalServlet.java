package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.utils.JournalXMLContainer;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.journalDAO.DeleteJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
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

@WebServlet(MappingConstants.DELETE_JOURNAL_MAPPING)
public class DeleteJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        String ids = req.getParameter(ServletConstants.PARAMETER_IDS);
        try {
            this.deleteJournal(ids);
        } catch (DeleteJournalException | GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        /*try {
            if (journalController != null)
                journalController.deleteJournal(ids);
        } catch (DeleteJournalException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }*/
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        String allJournals = null;
        try {
            allJournals = this.parseJournalListToXml(userId);
        } catch (GetConnectionException | GetAllJournalByUserIdException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        /*try {
            if (journalController != null)
                journalController.deleteJournal(ids);
        } catch (DeleteJournalException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        try {
            allJournals = xmlParser.toXML(new JournalXMLContainer(journalController.getAll(userId)));
        } catch (GetAllJournalByUserIdException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }*/
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                allJournals);
        resp.sendRedirect(MappingConstants.JOURNALS_PAGE_MAPPING);
        //requestDispatcher.forward(req, resp);
    }

    private void deleteJournal(String ids) throws DeleteJournalException, GetConnectionException {
        JournalController journalController = JournalController.getInstance();
        journalController.deleteJournal(ids);
    }

    private String parseJournalListToXml(int userId) throws GetConnectionException, GetAllJournalByUserIdException,
            ParseXMLException {
        JournalController journalController = JournalController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new JournalXMLContainer(journalController.getAll(userId)));
    }
}
