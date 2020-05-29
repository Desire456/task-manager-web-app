package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.utils.JournalXMLContainer;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.CreateJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.factories.JournalFactory;
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
import java.time.LocalDateTime;

/**
 * Add new journal and show journals.jsp
 */
@WebServlet(MappingConstants.ADD_JOURNAL_MAPPING)
public class AddJournalServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        boolean isPrivate = req.getParameter(ServletConstants.PARAMETER_ACCESS_MODIFIER) == null;
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        try {
            this.addJournal(name, description, userId, isPrivate);
        } catch (GetConnectionException | CreateJournalException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
            return;
        } catch (NameAlreadyExistException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_JOURNAL);
            requestDispatcher.forward(req, resp);
            return;
        }
        String allJournalsXml;
        try {
            allJournalsXml = this.parseJournalListToXml(userId);
        } catch (GetConnectionException | GetAllJournalByUserIdException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
            return;
        }
        if (allJournalsXml != null) {
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                    allJournalsXml);
            resp.sendRedirect(MappingConstants.JOURNALS_PAGE_MAPPING);
        }
    }

    private String parseJournalListToXml(int userId) throws GetConnectionException, GetAllJournalByUserIdException,
            ParseXMLException {
        JournalController journalController = JournalController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new JournalXMLContainer(journalController.getAll(userId)));
    }

    private void addJournal(String name, String description, int userId, boolean isPrivate)
            throws GetConnectionException, CreateJournalException, NameAlreadyExistException {
        JournalController journalController = JournalController.getInstance();
        journalController.addJournal(JournalFactory.createJournal(name, description,
                userId, LocalDateTime.now(), isPrivate));
    }
}
