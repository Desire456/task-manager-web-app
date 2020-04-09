package org.netcracker.students.servlets;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.controller.utils.TaskXMLContainer;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.DeleteTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteTask")
public class DeleteTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        TaskController taskController = null;
        try {
            taskController = TaskController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        XMLParser xmlParser = XMLParser.getInstance();
        HttpSession httpSession = req.getSession();
        int journalId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        String ids = req.getParameter(ServletConstants.PARAMETER_IDS);
        try {
            if (taskController != null)
                taskController.deleteTasks(ids);
        } catch (DeleteTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        String allTasks = null;
        try {
            allTasks = xmlParser.toXML(new TaskXMLContainer(taskController.getAll(journalId)));
        } catch (GetAllTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasks);
        requestDispatcher.forward(req, resp);
    }
}
