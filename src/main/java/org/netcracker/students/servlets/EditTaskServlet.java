package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalsController;
import org.netcracker.students.controller.TasksController;
import org.netcracker.students.controller.utils.IdGenerator;
import org.netcracker.students.controller.utils.xml.Journals;
import org.netcracker.students.controller.utils.xml.Tasks;
import org.netcracker.students.controller.utils.xml.XMLParser;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/editTask")
public class EditTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TasksController tasksController = TasksController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        String plannedDate = req.getParameter(ServletConstants.PARAMETER_PLANNED_DATE);
        int journalId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_JOURNAL_ID));
        req.setAttribute(ServletConstants.PARAMETER_JOURNAL_ID, journalId);
        int taskId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_ID));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ServletConstants.TIME_PATTERN);
        LocalDateTime parsedPlannedDate = LocalDateTime.parse(plannedDate, formatter);
        tasksController.changeTask(journalId + 1, taskId + 2, new Task(taskId + 2, name, description,
                parsedPlannedDate, null, ServletConstants.STATUS_PLANNED));
        String allTasks = xmlParser.toXML(new Tasks(tasksController.getAll(journalId + 1)));
        req.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS,
                allTasks);
        req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE).forward(req, resp);
    }
}
