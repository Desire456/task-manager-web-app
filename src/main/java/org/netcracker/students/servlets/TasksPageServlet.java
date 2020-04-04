package org.netcracker.students.servlets;

import org.netcracker.students.controller.TasksController;
import org.netcracker.students.controller.utils.Tasks;
import org.netcracker.students.controller.utils.XMLParser;
import org.netcracker.students.dao.exception.taskDAO.GetAllTaskException;
import org.netcracker.students.model.Task;

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
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
        int journalId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_JOURNAL_ID));
        HttpSession session = req.getSession();
        session.setAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID, journalId);
        TasksController tasksController = TasksController.getInstance();
        List<Task> taskArrayList = null;
        try {
            taskArrayList = tasksController.getAll(journalId);
        } catch (GetAllTaskException e) {
            e.printStackTrace();
        }
        if (taskArrayList != null && taskArrayList.isEmpty()) {
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, null);
        }  else {
            XMLParser xmlParser = XMLParser.getInstance();
            String allTasks = xmlParser.toXML(new Tasks(taskArrayList));
            session.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasks);
        }
        requestDispatcher.forward(req, resp);
    }
}
