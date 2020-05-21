package org.netcracker.students.strategy.importing.journal;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.CreateJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.JournalIdAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.UpdateJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.model.Journal;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.importing.exceptions.ImportException;
import org.netcracker.students.strategy.importing.ImportStrategy;
import org.netcracker.students.strategy.importing.exceptions.PrintableImportException;

public class JournalOverwriteImportStrategy implements ImportStrategy<Journal> {
    @Override
    public void store(Journal journal) throws ImportException, PrintableImportException {
        try {
            JournalController journalController = JournalController.getInstance();

            Journal oldJournal = journalController.getJournal(journal.getId());

            if (oldJournal != null) journalController.editJournal(journal);
            else journalController.addJournalWithId(journal);
        } catch (GetConnectionException | UpdateJournalException | ReadJournalException | CreateJournalException e) {
            throw new ImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (NameAlreadyExistException e) {
            throw new PrintableImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE +
                    e.getMessage());
        } catch (JournalIdAlreadyExistException ignored) {
        }
    }
}
