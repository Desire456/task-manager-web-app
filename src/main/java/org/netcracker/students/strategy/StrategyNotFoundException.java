package org.netcracker.students.strategy;

/**
 * Indicates lack of strategy in properties file
 */
public class StrategyNotFoundException extends Exception {
    public StrategyNotFoundException() {
    }

    public StrategyNotFoundException(String message) {
        super(message);
    }

    public StrategyNotFoundException(Throwable cause) {
        super(cause);
    }
}
