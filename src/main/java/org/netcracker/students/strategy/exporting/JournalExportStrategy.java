package org.netcracker.students.strategy.exporting;

import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;

import java.util.List;

public class JournalExportStrategy implements ExportStrategy {

    @Override
    public void collectExportIds(ExportList exportList, List<Integer> ids) throws GetConnectionException, ReadJournalException {
        exportList.addJournals(ids);
    }
}
