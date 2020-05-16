package org.netcracker.students.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.controller.utils.PropertyParser;
import org.netcracker.students.ejb.interfaces.IExportImport;
import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.servlets.constants.MappingConstants;
import org.netcracker.students.servlets.constants.ServletConstants;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.importing.ImportException;
import org.netcracker.students.strategy.importing.ImportStrategy;
import org.netcracker.students.strategy.importing.ImportStrategyHelper;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            xml = files.get(0).getString();
        }

        //unmarshal xml

        //fill this when unmarshal
        List<Journal> journals = null;
        List<Task> tasks = null;

        try {
            ImportStrategyHelper importStrategyHelper = ImportStrategyHelper.getInstance();

            int strategyId = this.getStrategyId(StrategyConstants.JOURNAL_IMPORT_STRATEGY);
            ImportStrategy<Journal> journalImportStrategy =
                    importStrategyHelper.resolveJournalStrategy(strategyId);
            for (Journal journal : journals) journalImportStrategy.store(journal);

            strategyId = this.getStrategyId(StrategyConstants.TASK_IMPORT_STRATEGY);
            ImportStrategy<Task> taskImportStrategy = importStrategyHelper.resolveTaskStrategy(strategyId);
            for(Task task : tasks) taskImportStrategy.store(task);

        } catch (PropertyFileException | ImportException e) {
            req.getRequestDispatcher(ServletConstants.PATH_TO_VIEW_JOURNALS_PAGE);
            req.setAttribute(ServletConstants.ATTRIBUTE_ERROR, e.getMessage());
        }

        resp.sendRedirect(MappingConstants.JOURNALS_PAGE_MAPPING);
    }

    private int getStrategyId(String strategyName) throws PropertyFileException {
        PropertyParser propertyParser = PropertyParser.getInstance();
        return Integer.parseInt(propertyParser.getProperty(strategyName));
    }
}
