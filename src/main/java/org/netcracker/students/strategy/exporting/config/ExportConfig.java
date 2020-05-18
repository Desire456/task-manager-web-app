package org.netcracker.students.strategy.exporting.config;

import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.controller.utils.PropertyParser;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.exporting.exceptions.ExportException;

import java.util.HashMap;
import java.util.Map;

public class ExportConfig {
    private static ExportConfig instance;
    private final Map<String, ExportConfigItem> configItems;

    private ExportConfig() throws ExportException {
        PropertyParser propertyParser;
        try {
            propertyParser = PropertyParser.getInstance();
        } catch (PropertyFileException e) {
            throw new ExportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        this.configItems = new HashMap<>();
        int journalStrategy = Integer.parseInt(propertyParser.getProperty(StrategyConstants.JOURNAL_EXPORT_STRATEGY));
        this.configItems.put(StrategyConstants.JOURNAL_TYPE, new ExportConfigItem(StrategyConstants.JOURNAL_TYPE,
                journalStrategy));

        int taskStrategy = Integer.parseInt(propertyParser.getProperty(StrategyConstants.TASK_EXPORT_STRATEGY));
        this.configItems.put(StrategyConstants.TASK_TYPE, new ExportConfigItem(StrategyConstants.TASK_TYPE, taskStrategy));
    }

    public static ExportConfig getInstance() throws ExportException {
        if (instance == null) {
            instance = new ExportConfig();
        }
        return instance;
    }

    public ExportConfigItem get(String type) {
        return this.configItems.get(type);
    }

}
