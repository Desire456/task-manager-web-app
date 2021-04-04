package org.netcracker.students.servlets;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.controller.utils.ParseXMLException;
import org.netcracker.students.controller.utils.TaskXMLContainer;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetFilteredByEqualsTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.GetFilteredByPatternTaskException;
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

/**
 * Filter tasks by column, like pattern or equal and criteria sort, show tasks.jsp
 */
@WebServlet(MappingConstants.FILTER_TASK_MAPPING)
public class FilterTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        String column = req.getParameter(ServletConstants.PARAMETER_COLUMN);
        String sortCriteria = req.getParameter(ServletConstants.PARAMETER_SORT);
        String equal = req.getParameter(ServletConstants.PARAMETER_EQUAL);
        String pattern = req.getParameter(ServletConstants.PARAMETER_PATTERN);
        HttpSession httpSession = req.getSession();
        int journalId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        List<TaskDTO> tasks = null;
        try {
            tasks = getFilteredTasks(journalId, column, pattern, sortCriteria, equal);
        } catch (GetConnectionException | GetFilteredByEqualsTaskException | GetFilteredByPatternTaskException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        String tasksXml = null;
        try {
            tasksXml = parseTaskListToXml(tasks);
        } catch (ParseXMLException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, tasksXml);
        requestDispatcher.forward(req, resp);
    }

    private List<TaskDTO> getFilteredTasks(int journalId, String column, String pattern, String sortCriteria,
                                           String equal) throws GetConnectionException,
            GetFilteredByEqualsTaskException, GetFilteredByPatternTaskException {
        TaskController taskController = TaskController.getInstance();
        return taskController.getFilteredTasks(journalId, column, pattern, sortCriteria, equal != null);
    }

    private String parseTaskListToXml(List<TaskDTO> taskList) throws ParseXMLException {
        XMLParser xmlParser = XMLParser.getInstance();
        return xmlParser.toXML(new TaskXMLContainer(taskList));
    }
}
