package org.netcracker.students.strategy.importing.task;

import org.netcracker.students.controller.TaskController;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.CreateTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.importing.exceptions.ImportConflictException;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.ImportStrategy;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

public class TaskConflictImportStrategy implements ImportStrategy<Task> {

    @Override
    public void store(Task task) throws ImportException, PrintableImportException {
        try {
            TaskController taskController = TaskController.getInstance();
            Task oldTask = taskController.getTask(task.getId());
            if (oldTask != null) throw new ImportConflictException();

            taskController.addTask(task);
        } catch (GetConnectionException | ReadTaskException e) {
            throw new ImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (ImportConflictException e) {
            String exceptionMessage = String.format(StrategyConstants.IMPORT_EXCEPTION_MATCH_ID_MESSAGE,
                    StrategyConstants.TASK_TYPE.toLowerCase(), task.getId());
            throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + exceptionMessage);
        } catch (CreateTaskException e) {
            String exceptionMessage = String.format(StrategyConstants.IMPORT_EXCEPTION_MATCH_NAME_MESSAGE,
                    StrategyConstants.TASK_TYPE.toLowerCase(), task.getName());
            throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + exceptionMessage);
        }
    }
}