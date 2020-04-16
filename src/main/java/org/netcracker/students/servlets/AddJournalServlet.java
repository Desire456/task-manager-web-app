package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.utils.JournalXMLContainer;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.journalDAO.CreateJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.factories.JournalFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/addJournal")
public class AddJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        JournalController journalController = null;
        try {
            journalController = JournalController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        XMLParser xmlParser = XMLParser.getInstance();
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        boolean isPrivate = req.getParameter(ServletConstants.PARAMETER_ACCESS_MODIFIER) == null;
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        String allJournals = null;
        try {
            if (journalController != null)
                journalController.addJournal(JournalFactory.createJournal(name, description,
                        userId, LocalDateTime.now(), isPrivate));
        } catch (CreateJournalException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_JOURNAL);
            requestDispatcher.forward(req, resp);
        }
        try {
            if (journalController != null)
                allJournals = xmlParser.toXML(new JournalXMLContainer(journalController.getAll(userId)));
        } catch (GetAllJournalByUserIdException | ParseXMLException e) {
            e.printStackTrace();
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                allJournals);
        requestDispatcher.forward(req, resp);
    }
}
