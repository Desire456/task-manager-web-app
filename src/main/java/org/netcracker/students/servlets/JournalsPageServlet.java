package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.UserController;
import org.netcracker.students.controller.utils.JournalXMLContainer;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.controller.utils.hashing.HashPasswordException;
import org.netcracker.students.controller.utils.hashing.HashingClass;
import org.netcracker.students.dao.exceptions.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.userDAO.GetUserByLoginException;
import org.netcracker.students.model.User;
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

/**
 * Get journals from database, marshal to xml and set session attribute to show journals on jsp
 */
@WebServlet(MappingConstants.JOURNALS_PAGE_MAPPING)
public class JournalsPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        String allJournalsXml = null;
        try {
            List<JournalDTO> journalList = this.getAllJournals(userId);
            allJournalsXml = this.parseJournalListToXml(journalList);
        } catch (GetConnectionException | GetAllJournalByUserIdException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, allJournalsXml);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        String login = req.getParameter(ServletConstants.PARAMETER_LOGIN);
        String password = req.getParameter(ServletConstants.PARAMETER_PASSWORD);
        User user = null;
        try {
            user = this.getUser(login, password);
        } catch (GetConnectionException | HashPasswordException | GetUserByLoginException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START).forward(req, resp);
        }
        if (user == null) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_CHECK_USER);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START).forward(req, resp);
        }
        int userId = user.getId();
        String allJournalsXml = null;
        try {
            List<JournalDTO> journalList = this.getAllJournals(userId);
            if (!journalList.isEmpty()) allJournalsXml = this.parseJournalListToXml(journalList);
        } catch (GetConnectionException | GetAllJournalByUserIdException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START).forward(req, resp);
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_USER_ID, userId);
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, allJournalsXml);
        requestDispatcher.forward(req, resp);
    }

    private String parseJournalListToXml(List<JournalDTO> journalDTOList) throws ParseXMLException {
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new JournalXMLContainer(journalDTOList));
    }

    private List<JournalDTO> getAllJournals(int userId) throws GetConnectionException, GetAllJournalByUserIdException {
        JournalController journalController = JournalController.getInstance();
        return journalController.getAll(userId);
    }

    private User getUser(String login, String password) throws GetConnectionException, GetUserByLoginException, HashPasswordException {
        UserController userController = UserController.getInstance();
        User user = userController.getUserByLogin(login);
        HashingClass hashingClass = HashingClass.getInstance();
        if (user != null) {
            user = hashingClass.validatePassword(password, user.getPassword()) ? user : null;
        }
        return user;
    }
}
