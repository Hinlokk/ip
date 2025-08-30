package Xiaodavid;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parser handles converting user input strings into structured commands
 * that can be executed by the application.
 */
public class Parser {

    /**
     * Represents a parsed user command.
     * Contains the command type and any arguments associated with it.
     */
    public static class ParsedCommand {
        public final CommandType type;
        public final String[] args;

        /**
         * Creates a ParsedCommand with the given type and arguments.
         *
         * @param type the type of the command
         * @param args optional arguments for the command
         */
        public ParsedCommand(CommandType type, String... args) {
            this.type = type;
            this.args = args;
        }
    }

    /**
     * Parses a string input into a ParsedCommand object.
     *
     * @param input the user input string
     * @return ParsedCommand representing the input
     * @throws XiaodavidException if the input is invalid
     */
    public static Parser.ParsedCommand parse(String input) throws XiaodavidException {
        String cmd = input.trim();

        if (cmd.equals("bye")) return new ParsedCommand(CommandType.BYE);
        if (cmd.equals("list")) return new ParsedCommand(CommandType.LIST);

        if (cmd.startsWith("mark")) {
            String rest = cmd.length() > 4 ? cmd.substring(5).trim() : "";
            if (rest.isEmpty())
                throw new XiaodavidException("ehh must specify which task number to mark la you goooon.");
            return new ParsedCommand(CommandType.MARK, rest);
        }

        if (cmd.startsWith("unmark")) {
            String rest = cmd.length() > 6 ? cmd.substring(7).trim() : "";
            if (rest.isEmpty())
                throw new XiaodavidException("ehh must specify which task number to unmark la you goooon.");
            return new ParsedCommand(CommandType.UNMARK, rest);
        }

        if (cmd.startsWith("delete")) {
            String rest = cmd.length() > 6 ? cmd.substring(7).trim() : "";
            if (rest.isEmpty())
                throw new XiaodavidException("ehh must specify which task number to delete la you goooon.");
            return new ParsedCommand(CommandType.DELETE, rest);
        }

        if (cmd.startsWith("todo")) {
            if (cmd.length() <= 4 || cmd.substring(5).trim().isEmpty()) {
                throw new XiaodavidException("the description of todo cannot be empty lehh you goooon.");
            }
            String desc = cmd.substring(5).trim();
            return new ParsedCommand(CommandType.TODO, desc);
        }

        if (cmd.startsWith("deadline")) {
            if (cmd.length() <= 8 || !cmd.contains("/by")) {
                throw new XiaodavidException("ehh deadline must have description and a /by time you goooon.");
            }
            String[] parts = cmd.substring(9).split("/by", 2);
            String desc = parts[0].trim();
            String byStr = parts.length > 1 ? parts[1].trim() : "";
            if (desc.isEmpty() || byStr.isEmpty()) {
                throw new XiaodavidException("ehh deadline description or /by cannot be empty you goooon.");
            }
            return new ParsedCommand(CommandType.DEADLINE, desc, byStr);
        }

        if (cmd.startsWith("event")) {
            if (cmd.length() <= 5 || !cmd.contains("/from") || !cmd.contains("/to")) {
                throw new XiaodavidException("ehh an event must have description, /from and /to you goooon.");
            }
            String[] parts = cmd.substring(6).split("/from|/to");
            if (parts.length < 3) {
                throw new XiaodavidException("ehh invalid event description laaa you goooon.");
            }
            String desc = parts[0].trim();
            String fromStr = parts[1].trim();
            String toStr = parts[2].trim();
            if (desc.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
                throw new XiaodavidException("ehh an event's description, /from and /to cannot be empty you goooon.");
            }
            return new ParsedCommand(CommandType.EVENT, desc, fromStr, toStr);
        }

        // unknown command
        return new ParsedCommand(CommandType.UNKNOWN);
    }

    /**
     * Converts a 1-based task number string to a 0-based index.
     *
     * @param s the string containing the task number
     * @return 0-based index
     * @throws NumberFormatException if the string is not a valid integer
     */
    public static int parseIndex(String s) throws NumberFormatException {
        return Integer.parseInt(s) - 1;
    }

    /**
     * Parses a date string into a LocalDate.
     *
     * @param s the date string (expects yyyy-MM-dd)
     * @return LocalDate object
     * @throws XiaodavidException if the date format is invalid
     */
    public static LocalDate parseDate(String s) throws XiaodavidException {
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException e) {
            throw new XiaodavidException("date format must be yyyy-mm-dd leh you goooon.");
        }
    }
}
