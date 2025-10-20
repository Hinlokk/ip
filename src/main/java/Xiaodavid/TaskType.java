package Xiaodavid;
/**
 * Enumerates the different task categories supported by the application.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String symbol;

    TaskType(String symbol) {
        this.symbol = symbol;
    }
    /**
     * Returns the single-character symbol used in saved files and UI.
     *
     * @return symbol representing the task type
     */
    public String getSymbol() {
        return symbol;
    }
}
