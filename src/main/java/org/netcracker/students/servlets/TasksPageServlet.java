package org.netcracker.students.servlets;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.TaskXMLContainer;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.model.dto.TaskDTO;
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
import java.util.List;

@WebServlet(MappingConstants.TASKS_PAGE_MAPPING)
public class TasksPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        HttpSession session = req.getSession();
        int journalId = (int) session.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        String allTasksXml = null;
        try {
            List<TaskDTO> taskList = this.getAllTasks(journalId);
            if(!taskList.isEmpty()) allTasksXml = this.parseTaskListToXml(taskList);
        } catch (GetConnectionException | GetAllTaskException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        int journalId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_JOURNAL_ID));
        HttpSession session = req.getSession();
        session.setAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID, journalId);
        String allTasksXml = null;
        try {
            List<TaskDTO> taskList = this.getAllTasks(journalId);
            if (!taskList.isEmpty()) allTasksXml = this.parseTaskListToXml(taskList);
        } catch (GetConnectionException | GetAllTaskException | ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        session.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasksXml);
        requestDispatcher.forward(req, resp);
    }

    private List<TaskDTO> getAllTasks(int journalId) throws GetConnectionException, GetAllTaskException {
        TaskController taskController = TaskController.getInstance();
        return taskController.getAll(journalId);
    }

    private String parseTaskListToXml(List<TaskDTO> taskList) throws ParseXMLException {
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new TaskXMLContainer(taskList));
    }
}
