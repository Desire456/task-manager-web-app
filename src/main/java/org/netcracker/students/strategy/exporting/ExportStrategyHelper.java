package org.netcracker.students.strategy.exporting;

import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.exporting.config.ExportConfigItem;
import org.netcracker.students.strategy.exporting.journal.JournalExportStrategy;
import org.netcracker.students.strategy.exporting.journal.JournalWithChildrenExportStrategy;
import org.netcracker.students.strategy.exporting.task.TaskExportStrategy;

import java.util.HashMap;
import java.util.Map;

public class ExportStrategyHelper {
    private static ExportStrategyHelper instance;
    private final Map<String, Map<Integer, ExportStrategy>> strategiesByType;

    public static ExportStrategyHelper getInstance() {
        if (instance == null) {
            instance = new ExportStrategyHelper();
        }
        return instance;
    }

    private ExportStrategyHelper() {
        Map<Integer, ExportStrategy> journalStrategies = new HashMap<>();
        journalStrategies.put(StrategyConstants.JOURNAL_EXPORT_ID, new JournalExportStrategy());
        journalStrategies.put(StrategyConstants.JOURNAL_WITH_CHILDREN_EXPORT_ID,
                new JournalWithChildrenExportStrategy());

        this.strategiesByType = new HashMap<>();
        this.strategiesByType.put(StrategyConstants.JOURNAL_TYPE, journalStrategies);

        Map<Integer, ExportStrategy> taskStrategies = new HashMap<>();
        taskStrategies.put(StrategyConstants.TASK_EXPORT_ID, new TaskExportStrategy());

        this.strategiesByType.put(StrategyConstants.TASK_TYPE, taskStrategies);

    }

    public ExportStrategy resolveStrategy(ExportConfigItem item) {
        return this.strategiesByType.get(item.getType()).get(item.getStrategyID());
    }
}
