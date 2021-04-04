package org.netcracker.students.servlets;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.dao.exceptions.journalDAO.DeleteJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete journals and show journals.jsp
 */
@WebServlet(MappingConstants.DELETE_JOURNAL_MAPPING)
public class DeleteJournalServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
		String ids = req.getParameter(ServletConstants.PARAMETER_IDS);
		try {
			JournalController journalController = JournalController.getInstance();
			journalController.deleteJournal(ids);
		} catch (DeleteJournalException | GetConnectionException e) {
			req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
			requestDispatcher.forward(req, resp);
			return;
		}
		resp.sendRedirect(MappingConstants.JOURNALS_PAGE_MAPPING);
	}
}
