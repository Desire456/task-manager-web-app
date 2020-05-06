package org.netcracker.students.servlets;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.TaskXMLContainer;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.UpdateTaskException;
import org.netcracker.students.factories.TaskFactory;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(MappingConstants.EDIT_TASK_MAPPING)
public class EditTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        String plannedDate = req.getParameter(ServletConstants.PARAMETER_PLANNED_DATE);
        HttpSession httpSession = req.getSession();
        int journalId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        int taskId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_ID));
        try {
            this.editTask(name, description, plannedDate, journalId, taskId);
        } catch (GetConnectionException | UpdateTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        String allTasks = null;
        try {
            allTasks = this.parseTaskListToXml(journalId);
        } catch (GetAllTaskException | ParseXMLException | GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasks);
        resp.sendRedirect(MappingConstants.TASKS_PAGE_MAPPING);
    }

    private void editTask(String name, String description, String plannedDate, int journalId, int taskId)
            throws GetConnectionException, UpdateTaskException {
        TaskController taskController = TaskController.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ServletConstants.TIME_PATTERN);
        LocalDateTime parsedPlannedDate = LocalDateTime.parse(plannedDate, formatter);
        taskController.editTask(TaskFactory.createTask(taskId, journalId, name, description, parsedPlannedDate,
                null, ServletConstants.STATUS_PLANNED));
    }

    private String parseTaskListToXml(int journalId) throws GetAllTaskException, GetConnectionException,
            ParseXMLException {
        TaskController taskController = TaskController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new TaskXMLContainer(taskController.getAll(journalId)));
    }
}
