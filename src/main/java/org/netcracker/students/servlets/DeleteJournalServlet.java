package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.Journals;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.journalDAO.DeleteJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exceptions.taskDAO.DeleteTaskException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteJournal")
public class DeleteJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        JournalsController journalsController = JournalsController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        String ids = req.getParameter(ServletConstants.PARAMETER_IDS);
        try {
            journalsController.deleteJournal(ids);
        } catch (DeleteJournalException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        String allJournals = null;
        try {
            allJournals = xmlParser.toXML(new Journals(journalsController.getAll(userId)));
        } catch (GetAllJournalByUserIdException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                allJournals);
        requestDispatcher.forward(req, resp);
    }
}
