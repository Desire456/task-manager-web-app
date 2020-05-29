package org.netcracker.students.strategy.exporting.config;

import org.netcracker.students.controller.utils.PropertyFileException;
import org.netcracker.students.controller.utils.PropertyParser;
import org.netcracker.students.strategy.StrategyConstants;
import org.netcracker.students.strategy.StrategyNotFoundException;
import org.netcracker.students.strategy.exporting.exceptions.PrintableExportException;

import java.util.HashMap;
import java.util.Map;

/**
 * Config class contains export strategies
 */
public class ExportConfig {
    private static ExportConfig instance;
    /**
     * Map contains strategies
     */
    private final Map<String, ExportConfigItem> configItems;

    /**
     * Fill strategies map
     * @throws PrintableExportException
     */
    private ExportConfig() throws PrintableExportException {
        PropertyParser propertyParser;
        try {
            propertyParser = PropertyParser.getInstance();
        } catch (PropertyFileException e) {
            throw new PrintableExportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        this.configItems = new HashMap<>();
        try {
            if (propertyParser.getProperty(StrategyConstants.JOURNAL_EXPORT_STRATEGY) == null) throw
                    new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } catch (StrategyNotFoundException e) {
            throw new PrintableExportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE +
                    e.getMessage());
        }
        int journalStrategy = Integer.parseInt(propertyParser.getProperty(StrategyConstants.JOURNAL_EXPORT_STRATEGY));
        this.configItems.put(StrategyConstants.JOURNAL_TYPE, new ExportConfigItem(StrategyConstants.JOURNAL_TYPE,
                journalStrategy));

        try {
            if (propertyParser.getProperty(StrategyConstants.TASK_EXPORT_STRATEGY) == null) throw
                    new StrategyNotFoundException(StrategyConstants.EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE);
        } catch (StrategyNotFoundException e) {
            throw new PrintableExportException(StrategyConstants.EXPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
        int taskStrategy = Integer.parseInt(propertyParser.getProperty(StrategyConstants.TASK_EXPORT_STRATEGY));
        this.configItems.put(StrategyConstants.TASK_TYPE, new ExportConfigItem(StrategyConstants.TASK_TYPE, taskStrategy));
    }

    public static ExportConfig getInstance() throws PrintableExportException {
        if (instance == null) {
            instance = new ExportConfig();
        }
        return instance;
    }

    /**
     * Getter method for config strategy by type
     * @param type entity type
     * @return strategy config
     */
    public ExportConfigItem get(String type) {
        return this.configItems.get(type);
    }

}
