package org.netcracker.students.strategy.exporting;

import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;

import java.util.List;

public class JournalWithChildrenExportStrategy implements ExportStrategy {
    @Override
    public void collectExportIds(ExportList exportList, List<Integer> ids) throws GetConnectionException,
            ReadJournalException, ReadTaskException, GetAllTaskException {
        exportList.addJournals(ids);
        exportList.addTasksByJournalId(ids);
    }
}
