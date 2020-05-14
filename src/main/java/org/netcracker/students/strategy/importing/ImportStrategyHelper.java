package org.netcracker.students.strategy.importing;

import org.netcracker.students.model.Journal;
import org.netcracker.students.model.Task;

import java.util.HashMap;
import java.util.Map;

public class ImportStrategyHelper {
    private static ImportStrategyHelper instance;
    private Map<Integer, ImportStrategy<Journal>> journalsStrategies; //journal strategies?
    private Map<Integer, ImportStrategy<Task>> taskStrategies;

    public static ImportStrategyHelper getInstance() {
        if(instance == null) {
            instance = new ImportStrategyHelper();
        }
        return instance;
    }

    private ImportStrategyHelper() {
        this.taskStrategies = new HashMap<>();
        this.taskStrategies.put(1, new TaskOverwriteImportStrategy());
        this.taskStrategies.put(2, new TaskIgnoreImportStrategy());
        this.taskStrategies.put(3, new TaskConflictImportStrategy());
    }

    public ImportStrategy<Journal> resolveJournalStrategy(int strategyID) {
        return this.journalsStrategies.get(strategyID);
    }

    public ImportStrategy<Task> resolveTaskStrategy(int strategyId) {
        return this.taskStrategies.get(strategyId);
    }
}
