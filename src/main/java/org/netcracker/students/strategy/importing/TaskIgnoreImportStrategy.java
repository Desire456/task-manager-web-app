package org.netcracker.students.strategy.importing;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.CreateTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.GetAllTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.StrategyConstants;

import java.util.List;

public class TaskIgnoreImportStrategy implements ImportStrategy<Task> {

    @Override
    public void store(Task task) throws ImportException {
        try {
            TaskController taskController = TaskController.getInstance();

            Task oldTask = taskController.getTask(task.getId());
            List<Task> tasks = taskController.getTaskByNameAndJournalId(task.getName(), task.getJournalId());

            if (oldTask == null && tasks.size() == 0) taskController.addTask(task);
        } catch (GetConnectionException | CreateTaskException | GetAllTaskException | ReadTaskException e) {
            throw new ImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
