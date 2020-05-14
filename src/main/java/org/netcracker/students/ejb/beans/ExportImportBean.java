package org.netcracker.students.ejb.beans;

import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.ejb.EJBConstants;
import org.netcracker.students.ejb.exceptions.ExportException;
import org.netcracker.students.ejb.interfaces.ExportImport;
import org.netcracker.students.factories.ExportListFactory;
import org.netcracker.students.strategy.exporting.ExportList;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ExportImportBean implements ExportImport {
    public void exportObjects(List<Integer> journalIds, List<Integer> tasksIds) throws ExportException {
        try {
            ExportList exportList = ExportListFactory.getInstance().create(journalIds, tasksIds);
        } catch (GetConnectionException | ReadTaskException | GetAllTaskException
                | ReadJournalException | PropertyFileException e) {
            throw new ExportException(EJBConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }

        //marshal export list
    }

    public void importObjects(String xml) {
        //unmarshal xml


    }
}
