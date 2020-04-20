package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.UserController;
import org.netcracker.students.controller.utils.hashing.HashingClass;
import org.netcracker.students.controller.utils.JournalXMLContainer;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.userDAO.GetUserByLoginException;
import org.netcracker.students.model.dto.JournalDTO;
import org.netcracker.students.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/journals")
public class JournalsPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        JournalController journalController = null;
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
            XMLParser xmlParser = XMLParser.getInstance();
            String allJournals = null;
            try {
                allJournals = xmlParser.toXML(new JournalXMLContainer(journalArrayList));
            } catch (ParseXMLException e) {
                req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
                requestDispatcher.forward(req, resp);
            }
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, allJournals);
        }
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        UserController userController = null;
        try {
            userController = UserController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        String login = req.getParameter(ServletConstants.PARAMETER_LOGIN);
        String password = req.getParameter(ServletConstants.PARAMETER_PASSWORD);
        User user = null;
        try {
            if (userController != null ){
                HashingClass hashingClass = HashingClass.getInstance();
                user = userController.getUserByLogin(login);
                if (user != null)
                    user = hashingClass.validatePassword(password, user.getPassword()) ? user : null;
            }
            if (user == null) {
                req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_CHECK_USER);
                req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START).forward(req, resp);
            }
        } catch (GetUserByLoginException  e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START).forward(req, resp);
        }
        int userId = -1;
        if (user != null) {
            userId = user.getId();
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_USER_ID, userId);
        JournalController journalController = null;
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
            XMLParser xmlParser = XMLParser.getInstance();
            String allJournals = null;
            try {
                allJournals = xmlParser.toXML(new JournalXMLContainer(journalArrayList));
            } catch (ParseXMLException e) {
                req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
                requestDispatcher.forward(req, resp);
            }
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, allJournals);
        }
        requestDispatcher.forward(req, resp);
    }
}
