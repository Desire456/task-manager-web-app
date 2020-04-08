package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.Journals;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.journalDAO.GetFilteredByEqualsJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.GetFilteredByPatternJournalException;
import org.netcracker.students.dto.JournalDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/filterJournals")
public class FilterJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        String column = req.getParameter(ServletConstants.PARAMETER_COLUMN);
        String sortCriteria = req.getParameter(ServletConstants.PARAMETER_SORT);
        String equal = req.getParameter(ServletConstants.PARAMETER_EQUAL);
        String pattern = req.getParameter(ServletConstants.PARAMETER_PATTERN);
        JournalsController journalsController = JournalsController.getInstance();
        HttpSession httpSession = req.getSession();
        int userId = (int)httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        List<JournalDTO> journals = null;
        try {
            journals = journalsController.getFilteredJournals(userId, column, pattern,
                    sortCriteria, equal != null);
        } catch (GetFilteredByEqualsJournalException | GetFilteredByPatternJournalException e) {
            e.printStackTrace();
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        XMLParser xmlParser = XMLParser.getInstance();
        String journalsXml = xmlParser.toXML(new Journals(journals));
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, journalsXml);
        requestDispatcher.forward(req, resp);
    }
}
