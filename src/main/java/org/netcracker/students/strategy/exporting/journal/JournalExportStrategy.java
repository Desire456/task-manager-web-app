package org.netcracker.students.strategy.exporting.journal;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.model.Journal;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.exporting.ExportList;
import org.netcracker.students.strategy.exporting.ExportStrategy;
import org.netcracker.students.strategy.exporting.exceptions.ExportException;

import java.util.List;

public class JournalExportStrategy implements ExportStrategy {

    @Override
    public void collectExportIds(ExportList exportList, List<Integer> ids) throws ExportException {
        try {
            JournalController journalController = JournalController.getInstance();
            List<Journal> journals = journalController.getJournals(ids);
            exportList.addJournals(journals);
        } catch (ReadJournalException | GetConnectionException e) {
            throw new ExportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
