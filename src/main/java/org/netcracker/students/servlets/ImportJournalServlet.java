package org.netcracker.students.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.ejb.interfaces.IExportImport;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(MappingConstants.IMPORT_JOURNAL_MAPPING)
public class ImportJournalServlet extends HttpServlet {
    @EJB
    private IExportImport EIBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
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
            xml = files.get(ServletConstants.FIRST_POS).getString();
        }

        resp.addHeader(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.NOT_ERROR_VALUE);
        int userId = (int) req.getSession().getAttribute(ServletConstants.ATTRIBUTE_USER_ID);
        try {
            EIBean.importObjects(xml, userId);
        } catch (PropertyFileException | ImportException e) {
            resp.setHeader(ServletConstants.ATTRIBUTE_ERROR, ServletConstants.COMMON_ERROR);
        } catch (PrintableImportException e) {
            resp.setHeader(ServletConstants.ATTRIBUTE_ERROR, e.getMessage());
        }
    }
}
