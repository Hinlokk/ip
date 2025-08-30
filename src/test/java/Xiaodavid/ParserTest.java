package Xiaodavid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void testParseTodo() throws XiaodavidException {
        Parser.ParsedCommand cmd = Parser.parse("todo read book");
        assertEquals(CommandType.TODO, cmd.type);
        assertEquals("read book", cmd.args[0]);
    }

    @Test
    public void testParseDeadline() throws XiaodavidException {
        Parser.ParsedCommand cmd = Parser.parse("deadline submit report /by 2025-09-01");
        assertEquals(CommandType.DEADLINE, cmd.type);
        assertEquals("submit report", cmd.args[0]);
        assertEquals("2025-09-01", cmd.args[1]);
    }
}
