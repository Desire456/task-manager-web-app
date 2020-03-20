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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@WebServlet("/add")
public class AddJournalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JournalsController journalsController = JournalsController.getInstance();
        XMLConverter xmlConverter = XMLConverter.getInstance();
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String date = LocalDateTime.now().format(formatter);
        journalsController.addJournal(new Journal(IdGenerator.getInstance().getId(), name,
                "private", LocalDateTime.now(), description));
        req.setAttribute("journals",
                journalsController.getAll());
        req.getRequestDispatcher("view/journals.jsp").forward(req, resp);
    }
}
