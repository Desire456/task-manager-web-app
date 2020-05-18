package org.netcracker.students.strategy.exporting.task;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.exporting.ExportList;
import org.netcracker.students.strategy.exporting.ExportStrategy;
import org.netcracker.students.strategy.exporting.exceptions.ExportException;

import java.util.List;

public class TaskExportStrategy implements ExportStrategy {

    @Override
    public void collectExportIds(ExportList exportList, List<Integer> ids) throws ExportException {
        try {
            TaskController taskController = TaskController.getInstance();
            List<Task> tasks = taskController.getTasks(ids);
            exportList.addTasks(tasks);
        } catch (ReadTaskException | GetConnectionException e) {
            throw new ExportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
