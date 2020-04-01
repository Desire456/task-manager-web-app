package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.xml.Journals;
import org.netcracker.students.controller.utils.xml.XMLParser;
import org.netcracker.students.factories.JournalFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/addJournal")
public class AddJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JournalsController journalsController = JournalsController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        String accessModifier = req.getParameter(ServletConstants.PARAMETER_ACCESS_MODIFIER);
        JournalFactory journalFactory = new JournalFactory();
        HttpSession httpSession = req.getSession();
        int userId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        String allJournals = null;
        try {
            journalsController.addJournal(journalFactory.createJournal(name, description,
                    userId, LocalDate.now(), accessModifier));
            allJournals = xmlParser.toXML(new Journals(journalsController.getAll(userId)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS,
                allJournals);
        req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE).forward(req, resp);
    }
}
