package org.netcracker.students.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/addTask")
public class AddTaskServlet extends HttpServlet {

    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TasksController tasksController = TasksController.getInstance();
        XMLParser xmlParser = XMLParser.getInstance();
        String name = req.getParameter(ServletConstants.PARAMETER_NAME);
        String description = req.getParameter(ServletConstants.PARAMETER_DESCRIPTION);
        String plannedDate = req.getParameter(ServletConstants.PARAMETER_PLANNED_DATE);
        int journalId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_JOURNAL_ID));
        req.setAttribute(ServletConstants.PARAMETER_JOURNAL_ID, journalId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ServletConstants.TIME_PATTERN);
        LocalDateTime parsedPlannedDate = LocalDateTime.parse(plannedDate, formatter);
        TaskFactory taskFactory = new TaskFactory();
        tasksController.addTask(journalId + 1, taskFactory.createTask(IdGenerator.getInstance().getId(), name, description,
                parsedPlannedDate, ServletConstants.STATUS_PLANNED));
        String allTasks = xmlParser.toXML(new Tasks(tasksController.getAll(journalId + 1)));
        req.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS,
                allTasks);
        req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE).forward(req, resp);
    }*/
}
