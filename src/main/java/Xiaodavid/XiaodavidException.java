package Xiaodavid;
/**
 * Represents application-specific checked exceptions thrown by Xiaodavid operations.
 */
public class XiaodavidException extends Exception {
    /**
     * Creates a new exception with the provided message.
     *
     * @param message explanation of the error condition
     */
    public XiaodavidException(String message) {

        super(message);
    }
}