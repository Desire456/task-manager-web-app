package org.netcracker.students.strategy.exporting;

import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;

import java.util.List;

public class TaskExportStrategy implements ExportStrategy {

    @Override
    public void collectExportIds(ExportList exportList, List<Integer> ids) throws GetConnectionException, ReadTaskException {
        exportList.addTasks(ids);
    }
}
