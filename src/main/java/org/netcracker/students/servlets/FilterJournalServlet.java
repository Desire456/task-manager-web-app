package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.utils.JournalXMLContainer;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.journalDAO.GetFilteredByEqualsJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.GetFilteredByPatternJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.model.dto.JournalDTO;
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
import java.util.List;

@WebServlet(MappingConstants.FILTER_JOURNAL_MAPPING)
public class FilterJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        String column = req.getParameter(ServletConstants.PARAMETER_COLUMN);
        String sortCriteria = req.getParameter(ServletConstants.PARAMETER_SORT);
        String equal = req.getParameter(ServletConstants.PARAMETER_EQUAL);
        String pattern = req.getParameter(ServletConstants.PARAMETER_PATTERN);
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        List<JournalDTO> journals = null;
        try {
            journals = this.getFilteredJournals(userId, column, pattern, sortCriteria, equal);
        } catch (GetConnectionException | GetFilteredByEqualsJournalException | GetFilteredByPatternJournalException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        String journalsXml = null;
        try {
            journalsXml = this.parseJournalListToXml(journals);
        } catch (ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, journalsXml);
        requestDispatcher.forward(req, resp);
    }

    private List<JournalDTO> getFilteredJournals(int userId, String column, String pattern, String sortCriteria,
                                                 String equal) throws GetConnectionException,
            GetFilteredByEqualsJournalException, GetFilteredByPatternJournalException {
        JournalController journalController = JournalController.getInstance();
        return journalController.getFilteredJournals(userId, column, pattern, sortCriteria, equal != null);
    }

    private String parseJournalListToXml(List<JournalDTO> journals) throws ParseXMLException {
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new JournalXMLContainer(journals));
    }
}
