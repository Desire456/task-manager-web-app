package org.netcracker.students.strategy.importing.journal;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.dao.exceptions.journalDAO.CreateJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.model.Journal;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.importing.exceptions.ImportConflictException;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.ImportStrategy;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

public class JournalConflictImportStrategy implements ImportStrategy<Journal> {
    @Override
    public void store(Journal journal) throws ImportException, PrintableImportException {
        try {
            JournalController journalController = JournalController.getInstance();

            Journal oldJournal = journalController.getJournal(journal.getId());
            if (isMatchId(journal, oldJournal)) throw new ImportConflictException();

            journalController.addJournal(journal);
        } catch (GetConnectionException | ReadJournalException e) {
            throw new ImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (ImportConflictException e) {
            String exceptionMessage = String.format(StrategyConstants.IMPORT_EXCEPTION_MATCH_ID_MESSAGE,
                    StrategyConstants.JOURNAL_TYPE.toLowerCase(), journal.getId());
            throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + exceptionMessage);
        } catch (CreateJournalException e) {
            String exceptionMessage = String.format(StrategyConstants.IMPORT_EXCEPTION_MATCH_NAME_MESSAGE,
                    StrategyConstants.JOURNAL_TYPE.toLowerCase(), journal.getName());
            throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + exceptionMessage);
        }
    }

    private boolean isMatchId(Journal task1, Journal task2) {
        return task1.getId() == task2.getId();
    }
}
