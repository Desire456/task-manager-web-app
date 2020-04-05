package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.UsersController;
import org.netcracker.students.controller.utils.Journals;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exception.journalDAO.GetAllJournalByUserIdException;
import org.netcracker.students.dao.exception.userDAO.GetUserByLoginAndPasswordException;
import org.netcracker.students.dao.exception.userDAO.GetUserByLoginException;
import org.netcracker.students.dto.JournalDTO;
import org.netcracker.students.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/journals")
public class JournalsPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        UsersController usersController = UsersController.getInstance();
        String login = req.getParameter(ServletConstants.PARAMETER_LOGIN);
        String password = req.getParameter(ServletConstants.PARAMETER_PASSWORD);
        User user = null;
        try {
            user = usersController.getUserByLoginAndPassword(login, password);
            if (user == null) {
                req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_USER);
                req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START).forward(req, resp);
            }
        } catch (GetUserByLoginAndPasswordException e) {
            e.printStackTrace();
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_START).forward(req, resp);
        }
        int userId = -1;
        if (user != null) {
            userId = user.getId();
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_USER_ID, userId);
        JournalsController journalsController = JournalsController.getInstance();
        List<JournalDTO> journalArrayList = null;
        try {
            journalArrayList = journalsController.getAll(userId);
        } catch (GetAllJournalByUserIdException e) {
            e.printStackTrace();
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        if (journalArrayList != null && journalArrayList.isEmpty()) {
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, null);
        } else {
            XMLParser xmlParser = XMLParser.getInstance();
            String allJournals = xmlParser.toXML(new Journals(journalArrayList));
            httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, allJournals);
        }
        requestDispatcher.forward(req, resp);
    }
}
