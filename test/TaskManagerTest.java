import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.schedule.manager.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    private TaskManager taskManager;
    private Task task1;
    private Task task2;
    private Epic epic;
    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
        task1 = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW, Duration.ofHours(2), LocalDateTime.of(2024, 8, 1, 10, 0));
        task2 = new Task(2, "Задача 2", "Описание 2", Task.Status.NEW, Duration.ofHours(2), LocalDateTime.of(2024, 8, 1, 11, 0));
        epic = new Epic(3, "Эпик 1", "Описание эпика");
        subtask1 = new Subtask(4, "Подзадача 1", "Описание подзадачи 1", Task.Status.NEW, 3);
        subtask2 = new Subtask(5, "Подзадача 2", "Описание подзадачи 2", Task.Status.NEW, 3);
    }

    @Test
    public void shouldAddAndGetTask() {
        taskManager.addTask(task1);
        Task retrievedTask = taskManager.getTask(task1.getId());
        assertEquals(task1, retrievedTask, "Задача должна быть добавлена и получена корректно.");
    }

    @Test
    public void shouldUpdateTask() {
        taskManager.addTask(task1);
        task1.setDescription("Обновленное описание");
        taskManager.updateTask(task1);
        Task updatedTask = taskManager.getTask(task1.getId());
        assertEquals("Обновленное описание", updatedTask.getDescription(), "Описание задачи должно быть обновлено.");
    }

    @Test
    public void shouldDeleteTask() {
        taskManager.addTask(task1);
        taskManager.deleteTask(task1.getId());
        Task deletedTask = taskManager.getTask(task1.getId());
        assertNull(deletedTask, "Задача должна быть удалена.");
    }

    @Test
    public void shouldAddAndGetEpic() {
        taskManager.addEpic(epic);
        Epic retrievedEpic = taskManager.getEpic(epic.getId());
        assertEquals(epic, retrievedEpic, "Эпик должен быть добавлен и получен корректно.");
    }

    @Test
    public void shouldUpdateEpic() {
        taskManager.addEpic(epic);
        epic.setDescription("Обновленное описание эпика");
        taskManager.updateEpic(epic);
        Epic updatedEpic = taskManager.getEpic(epic.getId());
        assertEquals("Обновленное описание эпика", updatedEpic.getDescription(), "Описание эпика должно быть обновлено.");
    }

    @Test
    public void shouldNotOverlapTasks() {
        taskManager.addTask(task1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            taskManager.addTask(task2);
        }, "Задачи должны пересекаться по времени.");

        assertEquals("Задачи пересекаются по времени.", exception.getMessage());
    }
}