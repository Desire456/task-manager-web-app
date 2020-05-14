package org.netcracker.students.strategy.exporting;

import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;

import java.util.List;

public interface ExportStrategy {
    void collectExportIds(ExportList exportList, List<Integer> ids) throws GetConnectionException, ReadTaskException, ReadJournalException, GetAllTaskException;
}
