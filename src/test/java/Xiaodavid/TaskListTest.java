package Xiaodavid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class TaskListTest {
    @Test
    public void testAddAndGetTask() {
        TaskList taskList = new TaskList();
        Deadline d = new Deadline("submit report", LocalDate.parse("2025-09-01"));

        taskList.add(d); // ✅ use add(), not addTask()
        assertEquals(1, taskList.size());
        assertEquals(d, taskList.get(0));
    }

    @Test
    public void testRemoveTask() {
        TaskList taskList = new TaskList();
        Deadline d = new Deadline("submit report", LocalDate.parse("2025-09-01"));

        taskList.add(d);
        Task removed = taskList.remove(0);

        assertEquals(d, removed);
        assertTrue(taskList.isEmpty());
    }
}
