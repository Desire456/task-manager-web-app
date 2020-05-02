package org.netcracker.students.servlets;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.TaskXMLContainer;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.CreateTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.factories.TaskFactory;
import org.netcracker.students.model.Task;
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

@WebServlet(MappingConstants.ADD_TASK_MAPPING)
public class AddTaskServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
     /*   TaskController taskController = null;
        try {
            taskController = TaskController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        XMLParser xmlParser = XMLParser.getInstance();*/
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        String plannedDate = req.getParameter(ServletConstants.PARAMETER_PLANNED_DATE);
        HttpSession httpSession = req.getSession();
        int journalId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        try {
            this.addTask(name, description, plannedDate, journalId);
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        } catch (CreateTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_TASK);
            requestDispatcher.forward(req, resp);
        }
        String allTasksXml = null;
        try {
            allTasksXml = this.parseTaskListToXml(journalId);
        } catch (GetAllTaskException | ParseXMLException | GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_TASK);
            requestDispatcher.forward(req, resp);
        }
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ServletConstants.TIME_PATTERN);
        LocalDateTime parsedPlannedDate = LocalDateTime.parse(plannedDate, formatter);
        try {
            if (taskController != null)
                taskController.addTask(TaskFactory.createTask(name, description,
                        parsedPlannedDate, ServletConstants.STATUS_PLANNED, journalId));
        } catch (CreateTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.ERROR_ADD_TASK);
            requestDispatcher.forward(req, resp);
        }
        String allTasks = null;
        try {
            if (taskController != null)
                allTasks = xmlParser.toXML(new TaskXMLContainer(taskController.getAll(journalId)));
        } catch (GetAllTaskException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }*/
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS,
                allTasksXml);
        resp.sendRedirect(MappingConstants.TASKS_PAGE_MAPPING);
        //requestDispatcher.forward(req, resp);
    }

    private void addTask(String name, String description, String plannedDate, int journalId)
            throws GetConnectionException, CreateTaskException {
        TaskController taskController = TaskController.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ServletConstants.TIME_PATTERN);
        LocalDateTime parsedPlannedDate = LocalDateTime.parse(plannedDate, formatter);
        taskController.addTask(TaskFactory.createTask(name, description,
                parsedPlannedDate, ServletConstants.STATUS_PLANNED, journalId));
    }

    private String parseTaskListToXml(int journalId) throws GetAllTaskException, GetConnectionException,
            ParseXMLException {
        TaskController taskController = TaskController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new TaskXMLContainer(taskController.getAll(journalId)));
    }
}
