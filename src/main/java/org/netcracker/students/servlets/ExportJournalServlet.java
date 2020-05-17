package org.netcracker.students.servlets;

import org.netcracker.students.ejb.exceptions.ExportException;
import org.netcracker.students.ejb.interfaces.IExportImport;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(MappingConstants.EXPORT_JOURNAL_MAPPING)
public class ExportJournalServlet extends HttpServlet {
    @EJB
    private IExportImport exportImportBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Integer> journalIds = this.parseStrIds(req.getParameter(ServletConstants.PARAMETER_IDS));
        try {
            exportImportBean.exportObjects(journalIds, new ArrayList<>());
        } catch (ExportException e) {
            e.printStackTrace();
        }
        String xml = "asd";
        String fileName = req.getParameter(ServletConstants.PARAMETER_FILENAME);
        ServletOutputStream out = resp.getOutputStream();
        byte[] bytes = xml.getBytes();
        resp.setHeader(ServletConstants.RESP_HEADER_NAME, ServletConstants.RESP_HEADER_VALUE + fileName
                + ServletConstants.FILE_EXTENSION);
        out.write(bytes);
        out.flush();
        out.close();
        resp.sendRedirect(MappingConstants.JOURNALS_PAGE_MAPPING);
    }

    private List<Integer> parseStrIds(String ids) {
        String[] arrIds = ids.split(" ");
        List<Integer> listIds = new ArrayList<>();
        for (String id : arrIds) listIds.add(Integer.parseInt(id));
        return listIds;
    }
}
