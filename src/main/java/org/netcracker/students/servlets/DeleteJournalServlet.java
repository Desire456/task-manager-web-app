package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.utils.XMLConverter;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Journals;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteJournalServlet extends HttpServlet {

    private static final String PARAMETER_ID_OF_JOURNAL = "ids";
    private static final String ATTRIBUTE_NAME = "journals";
    private static final String PATH_TO_VIEW = "view/journals.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JournalsController journalsController = JournalsController.getInstance();
        XMLConverter xmlConverter = XMLConverter.getInstance();
        String ids = req.getParameter(PARAMETER_ID_OF_JOURNAL);
        journalsController.removeJournal(ids);
        String allJournals = xmlConverter.toXML(new Journals(journalsController.getAll()));
        req.setAttribute(ATTRIBUTE_NAME,
                allJournals);
        req.getRequestDispatcher(PATH_TO_VIEW).forward(req, resp);
    }
}
