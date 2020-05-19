package org.netcracker.students.strategy.importing.task;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.controller.TaskController;
import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.dao.exceptions.taskDAO.CreateTaskException;
import org.netcracker.students.dao.exceptions.taskDAO.ReadTaskException;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.ImportStrategy;
import org.netcracker.students.strategy.importing.exceptions.ImportJournalNotFoundException;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

public class TaskIgnoreImportStrategy implements ImportStrategy<Task> {

    @Override
    public void store(Task task) throws ImportException, PrintableImportException {
        try {
            TaskController taskController = TaskController.getInstance();
            JournalController journalController = JournalController.getInstance();

            Task oldTask = taskController.getTask(task.getId());

            if (journalController.getJournal(task.getJournalId()) == null) throw new ImportJournalNotFoundException();
            if (oldTask == null) taskController.addTaskWithId(task);
        } catch (GetConnectionException | ReadTaskException | ReadJournalException |
                CreateTaskException e) {
            throw new ImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (ImportJournalNotFoundException e) {
            String exceptionMessage = String.format(StrategyConstants.IMPORT_EXCEPTION_JOURNAL_NOT_FOUND_MESSAGE,
                    task.getName());
            throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE +
                    exceptionMessage);
        } catch (NameAlreadyExistException ignored) {

        }
    }
}