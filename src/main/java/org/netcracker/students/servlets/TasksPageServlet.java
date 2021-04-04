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

/**
 * Get tasks from database, marshal to xml and set session attribute to show tasks on jsp
 */
@WebServlet(MappingConstants.TASKS_PAGE_MAPPING)
public class TasksPageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
		HttpSession session = req.getSession();
		int journalId = (int) session.getAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID);
		setTasksAndForward(req, resp, requestDispatcher, session, journalId);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_TASKS_PAGE);
		int journalId = Integer.parseInt(req.getParameter(ServletConstants.PARAMETER_JOURNAL_ID));
		HttpSession session = req.getSession();
		session.setAttribute(ServletConstants.ATTRIBUTE_JOURNAL_ID, journalId);
		setTasksAndForward(req, resp, requestDispatcher, session, journalId);
	}

	private void setTasksAndForward(HttpServletRequest req, HttpServletResponse resp, RequestDispatcher requestDispatcher, HttpSession session, int journalId) throws ServletException, IOException {
		String allTasksXml = null;
		try {
			TaskController taskController = TaskController.getInstance();
			List<TaskDTO> taskList = taskController.getAll(journalId);
			XMLParser xmlParser = XMLParser.getInstance();
			if (!taskList.isEmpty()) {
				allTasksXml = xmlParser.toXML(new TaskXMLContainer(taskList));
			}
		} catch (GetConnectionException | GetAllTaskException | ParseXMLException e) {
			req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
			requestDispatcher.forward(req, resp);
		}
		session.setAttribute(ServletConstants.ATTRIBUTE_NAME_OF_TASKS, allTasksXml);
		requestDispatcher.forward(req, resp);
	}
}
