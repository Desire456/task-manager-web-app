package org.netcracker.students.strategy.exporting.config;

public class ExportConfigItem {
    private final String type;
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
