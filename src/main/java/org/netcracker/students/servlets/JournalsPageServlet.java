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
        /*JournalController journalController = null;
        try {
            journalController = JournalController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        List<JournalDTO> journalArrayList = null;
        try {
            if (journalController != null)
                journalArrayList = journalController.getAll(userId);
        } catch (GetAllJournalByUserIdException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        if (journalArrayList != null && journalArrayList.isEmpty()) {
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, null);
        } else {
            String allJournals = null;
            try {
                allJournals = this.parseListToXml(journalArrayList);
            } catch (ParseXMLException e) {
                req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
                requestDispatcher.forward(req, resp);
            }
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, allJournals);
        }*/
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
            requestDispatcher.forward(req, resp);
        }
        if (user == null) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_CHECK_USER);
            requestDispatcher.forward(req, resp);
        }
        int userId = user.getId();
        String allJournalsXml = null;
        try {
            List<JournalDTO> journalList = this.getAllJournals(userId);
            if (!journalList.isEmpty()) allJournalsXml = this.parseJournalListToXml(journalList);
        } catch (GetConnectionException | GetAllJournalByUserIdException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_USER_ID, userId);
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, allJournalsXml);
        /*JournalController journalController = null;
        try {
            journalController = JournalController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        List<JournalDTO> journalArrayList = null;
        try {
            if (journalController != null)
                journalArrayList = journalController.getAll(userId);
        } catch (GetAllJournalByUserIdException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        if (journalArrayList != null && journalArrayList.isEmpty()) {
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, null);
        } else {
            String allJournals = null;
            try {
                allJournals = parseListToXml(journalArrayList);
            } catch (ParseXMLException e) {
                req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
                requestDispatcher.forward(req, resp);
            }
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, allJournals);
        }*/
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
            String hashPassword = hashingClass.hashPassword(password);
            user = hashingClass.validatePassword(password, hashPassword) ? user : null;
        }
        return user;
    }
}
