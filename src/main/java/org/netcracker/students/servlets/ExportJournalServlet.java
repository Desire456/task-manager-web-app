package org.netcracker.students.servlets;

import org.netcracker.students.ejb.exceptions.ExportException;
import org.netcracker.students.ejb.interfaces.IExportImport;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        String xml = "<Hello></Hello>";

        File file = new File("asd.xml");

            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(xml);
            ServletContext context = getServletContext();
            ServletOutputStream out = resp.getOutputStream();
            byte[] bytes = xml.getBytes();
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + "example.xml"+ "\"");
            out.write(bytes);
            out.flush();
            out.close();


       /* try {
            PropertyParser propertyParser = PropertyParser.getInstance();
            File file = new File(propertyParser.getProperty("uploadPath") + "journal.xml");
            if (!file.exists()) file.mkdirs();

        } catch (PropertyFileException e) {
            e.printStackTrace();
        }*/


            resp.sendRedirect(MappingConstants.JOURNALS_PAGE_MAPPING);
    }

    private List<Integer> parseStrIds(String ids) {
        String[] arrIds = ids.split(" ");
        List<Integer> listIds = new ArrayList<>();
        for (String id : arrIds) listIds.add(Integer.parseInt(id));
        return listIds;
    }
}
