package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.IdGenerator;
import org.netcracker.students.controller.utils.XMLConverter;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Journals;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/add")
public class AddJournalServlet extends HttpServlet {
    private static final String PARAMETER_NAME_OF_JOURNAL = "name";
    private static final String PARAMETER_DESCRIPTION_OF_JOURNAL = "description";
    private static final String ACCESS_MODIFIER = "private";
    private static final String ATTRIBUTE_NAME = "journals";
    private static final String PATH_TO_VIEW = "view/journals.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JournalsController journalsController = JournalsController.getInstance();
        XMLConverter xmlConverter = XMLConverter.getInstance();
        String name = req.getParameter(PARAMETER_NAME_OF_JOURNAL);
        String description = req.getParameter(PARAMETER_DESCRIPTION_OF_JOURNAL);
        journalsController.addJournal(new Journal(IdGenerator.getInstance().getId(), name,
                ACCESS_MODIFIER, LocalDateTime.now(), description));
        String allJournals = xmlConverter.toXML(new Journals(journalsController.getAll()));
        req.setAttribute(ATTRIBUTE_NAME,
                allJournals);
        req.getRequestDispatcher(PATH_TO_VIEW).forward(req, resp);
    }
}
