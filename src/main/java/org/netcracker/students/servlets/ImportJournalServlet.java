package org.netcracker.students.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.ejb.interfaces.IExportImport;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(MappingConstants.IMPORT_JOURNAL_MAPPING)
public class ImportJournalServlet extends HttpServlet {
    @EJB
    private IExportImport EIBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> files = null;
        try {
            files = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        String xml = null;
        if (files != null) {
            System.out.println(files.get(0).getName());
            xml = files.get(0).getString();
        }


        List<Journal> journals = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();


        resp.addHeader("answer" , "0");
        try {
            EIBean.importObjects(journals, tasks, xml,
                    (int) req.getSession().getAttribute(ServletConstants.ATTRIBUTE_USER_ID));

        } catch (PropertyFileException | ImportException e) {
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
            resp.setHeader("answer", ServletConstants.COMMON_ERROR);
          //  req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
        } catch (PrintableImportException e) {
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, e.getMessage());
            resp.setHeader("answer", e.getMessage());
            //req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE).forward(req, resp);
        }
    }
}
