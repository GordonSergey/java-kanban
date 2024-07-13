import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTasksWithSameIdAreEqual() {
        Task task1 = new Task(1, "Task 1", "Description 1", Task.Status.NEW);
        Task task2 = new Task(1, "Task 2", "Description 2", Task.Status.IN_PROGRESS);

        assertEquals(task1, task2, "Tasks with the same ID should be equal");
    }

    @Test
    public void testSubtasksWithSameIdAreEqual() {
        Subtask subtask1 = new Subtask(1, "Subtask 1", "Description 1", Task.Status.NEW, 100);
        Subtask subtask2 = new Subtask(1, "Subtask 2", "Description 2", Task.Status.IN_PROGRESS, 100);

        assertEquals(subtask1, subtask2, "Subtasks with the same ID should be equal");
    }

    @Test
    public void testEpicsWithSameIdAreEqual() {
        Epic epic1 = new Epic(1, "Epic 1", "Description 1");
        Epic epic2 = new Epic(1, "Epic 2", "Description 2");

        assertEquals(epic1, epic2, "Epics with the same ID should be equal");
    }
}