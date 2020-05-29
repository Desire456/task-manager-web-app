package org.netcracker.students.strategy.importing;

import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.importing.journal.JournalConflictImportStrategy;
import org.netcracker.students.strategy.importing.journal.JournalIgnoreImportStrategy;
import org.netcracker.students.strategy.importing.journal.JournalOverwriteImportStrategy;
import org.netcracker.students.strategy.importing.task.TaskConflictImportStrategy;
import org.netcracker.students.strategy.importing.task.TaskIgnoreImportStrategy;
import org.netcracker.students.strategy.importing.task.TaskOverwriteImportStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Class helper contains strategies by type and id and can return needed strategy by strategy id
 */
public class ImportStrategyHelper {
    private static ImportStrategyHelper instance;
    private final Map<Integer, ImportStrategy<Journal>> journalsStrategies;
    private final Map<Integer, ImportStrategy<Task>> taskStrategies;

    public static ImportStrategyHelper getInstance() {
        if(instance == null) {
            instance = new ImportStrategyHelper();
        }
        return instance;
    }

    /**
     * Fill strategies map
     */
    private ImportStrategyHelper() {
        this.taskStrategies = new HashMap<>();
        this.taskStrategies.put(StrategyConstants.TASK_OVERWRITE_IMPORT_ID, new TaskOverwriteImportStrategy());
        this.taskStrategies.put(StrategyConstants.TASK_IGNORE_IMPORT_ID, new TaskIgnoreImportStrategy());
        this.taskStrategies.put(StrategyConstants.TASK_CONFLICT_IMPORT_ID, new TaskConflictImportStrategy());
        this.journalsStrategies = new HashMap<>();
        this.journalsStrategies.put(StrategyConstants.JOURNAL_OVERWRITE_IMPORT_ID,
                new JournalOverwriteImportStrategy());
        this.journalsStrategies.put(StrategyConstants.JOURNAL_IGNORE_IMPORT_ID,
                new JournalIgnoreImportStrategy());
        this.journalsStrategies.put(StrategyConstants.JOURNAL_CONFLICT_IMPORT_ID,
                new JournalConflictImportStrategy());
    }

    /**
     * Resolve journal strategy by id
     * @param strategyID
     * @return needed import strategy
     */
    public ImportStrategy<Journal> resolveJournalStrategy(int strategyID) {
        return this.journalsStrategies.get(strategyID);
    }

    /**
     * Resolve task strategy by id
     * @param strategyId
     * @return needed import strategy
     */
    public ImportStrategy<Task> resolveTaskStrategy(int strategyId) {
        return this.taskStrategies.get(strategyId);
    }
}
