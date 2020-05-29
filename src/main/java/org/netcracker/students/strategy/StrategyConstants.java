package org.netcracker.students.strategy;

public class StrategyConstants {
    public static final String JOURNAL_TYPE = "Journal";
    public static final String TASK_TYPE = "Task";
    public static final String JOURNAL_EXPORT_STRATEGY = "journalExportStrategy";
    public static final String JOURNAL_IMPORT_STRATEGY = "journalImportStrategy";
    public static final String TASK_EXPORT_STRATEGY = "taskExportStrategy";
    public static final String TASK_IMPORT_STRATEGY = "taskImportStrategy";
    public static final String IMPORT_EXCEPTION_MESSAGE = "Can't import ";
    public static final String EXPORT_EXCEPTION_MESSAGE = "Can't export ";
    public static final String EXPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE = "export strategy not found";
    public static final String IMPORT_STRATEGY_NOT_FOUND_EXCEPTION_MESSAGE = "import strategy not found";
    public static final String IMPORT_EXCEPTION_JOURNAL_NOT_FOUND_MESSAGE = "task with name " +
            "%s because there is no journal for her";
    public static final int JOURNAL_EXPORT_ID = 1;
    public static final int JOURNAL_WITH_CHILDREN_EXPORT_ID = 2;
    public static final int TASK_EXPORT_ID = 1;
    public static final int TASK_OVERWRITE_IMPORT_ID = 1;
    public static final int TASK_IGNORE_IMPORT_ID = 2;
    public static final int TASK_CONFLICT_IMPORT_ID = 3;
    public static final int JOURNAL_OVERWRITE_IMPORT_ID = 1;
    public static final int JOURNAL_IGNORE_IMPORT_ID = 2;
    public static final int JOURNAL_CONFLICT_IMPORT_ID = 3;
}
