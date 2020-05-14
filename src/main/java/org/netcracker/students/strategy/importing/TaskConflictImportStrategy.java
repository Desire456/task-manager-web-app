package org.netcracker.students.strategy.importing;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.CreateTaskException;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.StrategyConstants;

public class TaskConflictImportStrategy implements ImportStrategy<Task> {

    @Override
    public void store(Task task) throws ImportException {
        try {
            TaskController taskController = TaskController.getInstance();
            taskController.addTask(task);
            //may be better detail conflict by throw exceptions by some conditions like 'match id', 'match name'
        } catch (CreateTaskException | GetConnectionException e) {
            throw new ImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
