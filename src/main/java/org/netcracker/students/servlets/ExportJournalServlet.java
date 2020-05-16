package org.netcracker.students.servlets;

import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.controller.utils.PropertyParser;
import org.netcracker.students.ejb.beans.ExportImportBean;
import org.netcracker.students.ejb.interfaces.IExportImport;
import org.netcracker.students.servlets.constants.MappingConstants;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(MappingConstants.EXPORT_JOURNAL_MAPPING)
public class ExportJournalServlet extends HttpServlet {
    @EJB
    private ExportImportBean EIBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Integer> journalIds = this.parseStrIds(req.getParameter("ids"));
       /* try {
            EIBean.exportObjects(journalIds, new ArrayList<>());
        } catch (ExportException e) {
            e.printStackTrace();
        }*/

        System.out.println(EIBean.asd());


        try {
            PropertyParser propertyParser = PropertyParser.getInstance();
            File file = new File(propertyParser.getProperty("uploadPath") + "journal.xml");
            if (!file.exists()) file.mkdirs();

        } catch (PropertyFileException e) {
            e.printStackTrace();
        }


        resp.sendRedirect(MappingConstants.JOURNALS_PAGE_MAPPING);
    }

    private List<Integer> parseStrIds(String ids) {
        String[] arrIds = ids.split(" ");
        List<Integer> listIds = new ArrayList<>();
        for (String id : arrIds) listIds.add(Integer.parseInt(id));
        return listIds;
    }
}
