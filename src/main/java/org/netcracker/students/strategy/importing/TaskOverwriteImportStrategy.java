package org.netcracker.students.strategy.importing;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.CreateTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.UpdateTaskException;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.StrategyConstants;

public class TaskOverwriteImportStrategy implements ImportStrategy<Task> {

    @Override
    public void store(Task task) throws ImportException {
        try {
            TaskController taskController = TaskController.getInstance();

            Task oldTask = taskController.getTask(task.getId());

            if (task.equals(oldTask)) taskController.editTask(task);
            else taskController.addTask(task);

        } catch (CreateTaskException | GetConnectionException | ReadTaskException | UpdateTaskException e) {
            throw new ImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
