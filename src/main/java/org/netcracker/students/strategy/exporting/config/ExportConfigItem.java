package org.netcracker.students.strategy.exporting.config;

/**
 * Export strategy config
 */
public class ExportConfigItem {
    /**
     * Entity type
     */
    private final String type;
    /**
     * Strategy id
     */
    public final Integer strategyID;

    public ExportConfigItem(String type, Integer strategyID) {
        this.type = type;
        this.strategyID = strategyID;
    }

    public String getType() {
        return type;
    }

    public Integer getStrategyID() {
        return strategyID;
    }
}
