package org.netcracker.students.servlets;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.TaskXMLContainer;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.model.dto.TaskDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TasksPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        HttpSession session = req.getSession();
        int journalId = (int) session.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        TaskController taskController = null;
        try {
            taskController = TaskController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        List<TaskDTO> taskArrayList = null;
        try {
            if (taskController != null)
                taskArrayList = taskController.getAll(journalId);
        } catch (GetAllTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        if (taskArrayList != null && taskArrayList.isEmpty()) {
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, null);
        } else {
            XMLParser xmlParser = XMLParser.getInstance();
            String allTasks = null;
            try {
                allTasks = xmlParser.toXML(new TaskXMLContainer(taskArrayList));
            } catch (ParseXMLException e) {
                req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
                requestDispatcher.forward(req, resp);
            }
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasks);
        }
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        int journalId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_JOURNAL_ID));
        HttpSession session = req.getSession();
        session.setAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID, journalId);
        TaskController taskController = null;
        try {
            taskController = TaskController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        List<TaskDTO> taskArrayList = null;
        try {
            if (taskController != null)
                taskArrayList = taskController.getAll(journalId);
        } catch (GetAllTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        if (taskArrayList != null && taskArrayList.isEmpty()) {
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, null);
        } else {
            XMLParser xmlParser = XMLParser.getInstance();
            String allTasks = null;
            try {
                allTasks = xmlParser.toXML(new TaskXMLContainer(taskArrayList));
            } catch (ParseXMLException e) {
                req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
                requestDispatcher.forward(req, resp);
            }
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasks);
        }
        requestDispatcher.forward(req, resp);
    }
}
