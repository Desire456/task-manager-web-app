package org.netcracker.students.servlets;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.TaskXMLContainer;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.UpdateTaskException;
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
import java.util.ArrayList;

@WebServlet(MappingConstants.FINISH_TASK_MAPPING)
public class FinishTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        /*TaskController taskController = null;
        try {
            taskController = TaskController.getInstance();
        } catch (GetConnectionException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        XMLParser xmlParser = XMLParser.getInstance();*/
        HttpSession httpSession = req.getSession();
        int journalId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        String ids = req.getParameter(ServletConstants.PARAMETER_IDS);
        try {
            this.finishTasks(ids);
        } catch (GetConnectionException | UpdateTaskException | ReadTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        /*try {
            if (taskController != null) {
                ArrayList<Task> tasks = taskController.getTasks(ids);
                taskController.finishTasks(tasks);
            }
        } catch (ReadTaskException | UpdateTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }*/
        String allTasksXml = null;
        try {
            allTasksXml = this.parseTaskListToXml(journalId);
        } catch (GetAllTaskException | GetConnectionException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
      /*  try {
            allTasks = xmlParser.toXML(new TaskXMLContainer(taskController.getAll(journalId)));
        } catch (GetAllTaskException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }*/
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasksXml);
        resp.sendRedirect(MappingConstants.TASKS_PAGE_MAPPING);
        //requestDispatcher.forward(req, resp);
    }

    private void finishTasks(String ids) throws GetConnectionException, UpdateTaskException, ReadTaskException {
        TaskController taskController = TaskController.getInstance();
        ArrayList<Task> tasks = taskController.getTasks(ids);
        taskController.finishTasks(tasks);
    }

    private String parseTaskListToXml(int journalId) throws GetAllTaskException, GetConnectionException,
            ParseXMLException {
        TaskController taskController = TaskController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new TaskXMLContainer(taskController.getAll(journalId)));
    }
}
