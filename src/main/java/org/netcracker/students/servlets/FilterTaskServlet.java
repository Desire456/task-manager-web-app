package org.netcracker.students.servlets;

import org.netcracker.students.controller.TasksController;
import org.netcracker.students.controller.utils.Tasks;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exception.taskDAO.GetFilteredByEqualsTaskException;
import org.netcracker.students.dao.exception.taskDAO.GetFilteredByPatternTaskException;
import org.netcracker.students.dto.TaskDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/filterTasks")
public class FilterTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        String column = req.getParameter(ServletConstants.PARAMETER_COLUMN);
        String sortCriteria = req.getParameter(ServletConstants.PARAMETER_SORT);
        String equal = req.getParameter(ServletConstants.PARAMETER_EQUAL);
        String pattern = req.getParameter(ServletConstants.PARAMETER_PATTERN);
        TasksController tasksController = TasksController.getInstance();
        HttpSession httpSession = req.getSession();
        int journalId = (int) httpSession.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        List<TaskDTO> tasks = null;
        try {
            tasks = tasksController.getFilteredTasks(journalId, column, pattern,
                    sortCriteria, equal != null);
        } catch (GetFilteredByEqualsTaskException | GetFilteredByPatternTaskException e) {
            e.printStackTrace();
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        }
        XMLParser xmlParser = XMLParser.getInstance();
        String journalsXml = xmlParser.toXML(new Tasks(tasks));
        httpSession.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_JOURNALS, journalsXml);
        requestDispatcher.forward(req, resp);
    }
}
