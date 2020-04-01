package org.netcracker.students.servlets;

import org.netcracker.students.controller.TasksController;
import org.netcracker.students.controller.utils.xml.Tasks;
import org.netcracker.students.controller.utils.xml.XMLParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteTask")
public class DeleteTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TasksController tasksController = TasksController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        HttpSession httpSession = req.getSession();
        int journalId = (int)httpSession.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
        String ids = req.getParameter(ServletConstants.PARAMETER_IDS);
        try {
            tasksController.deleteTask(ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String allTasks = null;
        try {
            allTasks = xmlParser.toXML(new Tasks(tasksController.getAll(journalId + 1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasks);
        req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE).forward(req, resp);
    }
}
