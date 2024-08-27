import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskConstructorWithAllFields() {
        Task task = new Task(1, "Test Task", "Description", Task.Status.NEW, Duration.ofHours(1), LocalDateTime.of(2024, 8, 1, 10, 0));
        assertEquals(1, task.getId());
        assertEquals("Test Task", task.getName());
        assertEquals("Description", task.getDescription());
        assertEquals(Task.Status.NEW, task.getStatus());
        assertEquals(Duration.ofHours(1), task.getDuration());
        assertEquals(LocalDateTime.of(2024, 8, 1, 10, 0), task.getStartTime());
    }

    @Test
    public void testTaskConstructorWithoutDurationAndStartTime() {
        Task task = new Task(2, "Another Task", "Another Description", Task.Status.IN_PROGRESS);
        assertEquals(2, task.getId());
        assertEquals("Another Task", task.getName());
        assertEquals("Another Description", task.getDescription());
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
        assertNull(task.getDuration());
        assertNull(task.getStartTime());
    }

    @Test
    public void testGetEndTime() {
        Task task = new Task(3, "Task with End Time", "Description", Task.Status.DONE, Duration.ofHours(2), LocalDateTime.of(2024, 8, 1, 10, 0));
        assertEquals(LocalDateTime.of(2024, 8, 1, 12, 0), task.getEndTime());
    }

    @Test
    public void testGetEndTimeWhenDurationOrStartTimeIsNull() {
        Task task = new Task(4, "Task without End Time", "Description", Task.Status.NEW);
        assertNull(task.getEndTime());
    }

    @Test
    public void testToString() {
        Task task = new Task(5, "ToString Test", "Description", Task.Status.NEW, Duration.ofMinutes(90), LocalDateTime.of(2024, 8, 1, 10, 0));
        // Ожидаемый результат строки должен совпадать с фактическим результатом
        assertEquals("5,ToString Test,Description,NEW,90,2024-08-01T10:00", task.toString());
    }
    @Test
    public void testEqualsAndHashCode() {
        Task task1 = new Task(6, "Task1", "Description", Task.Status.NEW, Duration.ofHours(1), LocalDateTime.of(2024, 8, 1, 10, 0));
        Task task2 = new Task(6, "Task1", "Description", Task.Status.NEW, Duration.ofHours(1), LocalDateTime.of(2024, 8, 1, 10, 0));
        Task task3 = new Task(7, "Task3", "Different Description", Task.Status.IN_PROGRESS, Duration.ofHours(2), LocalDateTime.of(2024, 8, 2, 11, 0));

        assertEquals(task1, task2, "Tasks with the same ID should be equal");
        assertNotEquals(task1, task3, "Tasks with different IDs should not be equal");

        assertEquals(task1.hashCode(), task2.hashCode(), "Hash codes should be the same for equal tasks");
        assertNotEquals(task1.hashCode(), task3.hashCode(), "Hash codes should be different for non-equal tasks");
    }

    @Test
    public void testFromString() {
        Task task = Task.fromString("8,FromString Test,Description,NEW,120,2024-08-01T10:00:00");
        assertEquals(8, task.getId());
        assertEquals("FromString Test", task.getName());
        assertEquals("Description", task.getDescription());
        assertEquals(Task.Status.NEW, task.getStatus());
        assertEquals(Duration.ofMinutes(120), task.getDuration());
        assertEquals(LocalDateTime.of(2024, 8, 1, 10, 0), task.getStartTime());
    }

    @Test
    public void testFromStringWithNullValues() {
        Task task = Task.fromString("9,Task with Nulls,Description,IN_PROGRESS,null,null");
        assertEquals(9, task.getId());
        assertEquals("Task with Nulls", task.getName());
        assertEquals("Description", task.getDescription());
        assertEquals(Task.Status.IN_PROGRESS, task.getStatus());
        assertNull(task.getDuration());
        assertNull(task.getStartTime());
    }

    @Test
    public void testFromStringWithInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> Task.fromString("Invalid format"));
        assertThrows(IllegalArgumentException.class, () -> Task.fromString("10,Task,Description,NEW,30"));
    }
}