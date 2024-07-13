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
        Task taskWithGivenId = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW);
        Task taskWithGeneratedId = new Task(0, "Задача 2", "Описание 2", Task.Status.NEW);

        Task addedTaskWithGivenId = taskManager.addTask(taskWithGivenId);
        Task addedTaskWithGeneratedId = taskManager.addTask(taskWithGeneratedId);

        assertNotEquals(addedTaskWithGivenId.getId(), addedTaskWithGeneratedId.getId(), "Сгенерированный ID не должен конфликтовать с заданным ID");
    }

    @Test
    public void testSubtaskCannotBeItsOwnEpic() {
        Subtask subtask = new Subtask(1, "Подзадача 1", "Описание подзадачи", Task.Status.NEW, 2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subtask.setEpicId(1);
        }, "Подзадача не может быть своим же эпиком.");

        assertEquals("Подзадача не может быть своим же эпиком.", exception.getMessage());
    }
}