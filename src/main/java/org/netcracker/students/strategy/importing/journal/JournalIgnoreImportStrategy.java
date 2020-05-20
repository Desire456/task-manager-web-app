package org.netcracker.students.strategy.importing.journal;

import org.netcracker.students.controller.JournalController;
import org.netcracker.students.dao.exceptions.GetByNameException;
import org.netcracker.students.dao.exceptions.NameAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.CreateJournalException;
import org.netcracker.students.dao.exceptions.journalDAO.JournalIdAlreadyExistException;
import org.netcracker.students.dao.exceptions.journalDAO.ReadJournalException;
import org.netcracker.students.dao.exceptions.managerDAO.GetConnectionException;
import org.netcracker.students.model.Journal;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.importing.ImportStrategy;
import org.netcracker.students.strategy.importing.exceptions.ImportException;

public class JournalIgnoreImportStrategy implements ImportStrategy<Journal> {
    @Override
    public void store(Journal journal) throws ImportException {
        try {
            JournalController journalController = JournalController.getInstance();

            Journal oldJournal = journalController.getJournal(journal.getId());

            if (oldJournal == null) journalController.addJournalWithId(journal);
        } catch (GetConnectionException | ReadJournalException | CreateJournalException | GetByNameException e) {
            throw new ImportException(StrategyConstants.IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        } catch (NameAlreadyExistException | JournalIdAlreadyExistException ignored) {

        }
    }
}
