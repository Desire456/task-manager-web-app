package org.netcracker.students.servlets;

import org.netcracker.students.ejb.interfaces.IExportImport;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;
import org.netcracker.students.strategy.exporting.exceptions.ExportException;
import org.netcracker.students.strategy.exporting.exceptions.PrintableExportException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Export xml with journals
 */
@WebServlet(MappingConstants.EXPORT_JOURNAL_MAPPING)
public class ExportJournalServlet extends HttpServlet {
    @EJB
    private IExportImport exportImportBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
        List<Integer> journalIds = parseStrIds(req.getParameter(ServletConstants.PARAMETER_IDS));
        String xml = null;
        try {
            xml = exportImportBean.exportObjects(journalIds, new ArrayList<>());
        } catch (ExportException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
            requestDispatcher.forward(req, resp);
        } catch (PrintableExportException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, e.getMessage());
            requestDispatcher.forward(req, resp);
        }

        if (xml != null) {
            String fileName = req.getParameter(ServletConstants.PARAMETER_FILENAME);
            ServletOutputStream out = resp.getOutputStream();
            byte[] bytes;
            bytes = xml.getBytes();

            resp.setHeader(ServletConstants.RESP_HEADER_NAME, ServletConstants.RESP_HEADER_VALUE + fileName
                    + ServletConstants.FILE_EXTENSION);
            out.write(bytes);
            out.flush();
            out.close();
        }
    }

    private List<Integer> parseStrIds(String ids) {
        String[] arrIds = ids.split(" ");
        List<Integer> listIds = new ArrayList<>();
        for (String id : arrIds) listIds.add(Integer.parseInt(id));
        return listIds;
    }
}
