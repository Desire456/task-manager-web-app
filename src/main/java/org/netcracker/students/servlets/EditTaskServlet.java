package org.netcracker.students.servlets;

import org.netcracker.students.controller.TasksController;
import org.netcracker.students.controller.utils.xml.Tasks;
import org.netcracker.students.controller.utils.xml.XMLParser;
import org.netcracker.students.dao.exception.taskDAO.GetAllTaskException;
import org.netcracker.students.factories.TaskFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
        HttpSession httpSession = req.getSession();
        int journalId = (int)httpSession.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        int taskId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_ID));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ServletConstants.TIME_PATTERN);
        LocalDateTime parsedPlannedDate = LocalDateTime.parse(plannedDate, formatter);
        TaskFactory taskFactory = new TaskFactory();
        tasksController.changeTask(taskFactory.createTask(taskId, journalId, name, description, parsedPlannedDate,
                    null, ServletConstants.STATUS_PLANNED));
        String allTasks = null;
        try {
            allTasks = xmlParser.toXML(new Tasks(tasksController.getAll(journalId)));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (GetAllTaskException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE).forward(req, resp);
    }
}
