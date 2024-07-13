import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void testNoConflictBetweenGivenAndGeneratedIds() {
        Task taskWithGivenId = new Task(1, "Task 1", "Description 1", Task.Status.NEW);
        Task taskWithGeneratedId = new Task(0, "Task 2", "Description 2", Task.Status.NEW);

        Task addedTaskWithGivenId = taskManager.addTask(taskWithGivenId);
        Task addedTaskWithGeneratedId = taskManager.addTask(taskWithGeneratedId);

        assertNotEquals(addedTaskWithGivenId.getId(), addedTaskWithGeneratedId.getId(), "Generated ID should not conflict with given ID");
    }

    @Test
    public void testSubtaskCannotBeItsOwnEpic() {
        Subtask subtask = new Subtask(1, "Subtask 1", "Subtask Description", Task.Status.NEW, 2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subtask.setEpicId(1);
        }, "Subtask cannot be its own epic.");

        assertEquals("Subtask cannot be its own epic.", exception.getMessage());
    }
}