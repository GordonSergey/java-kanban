import ru.yandex.javacourse.schedule.manager.Epic;
import ru.yandex.javacourse.schedule.manager.InMemoryTaskManager;
import ru.yandex.javacourse.schedule.manager.Subtask;
import ru.yandex.javacourse.schedule.manager.Task;
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
    public void testAddAndRemoveTask() {
        Task task = new Task(1, "Задача 1", "Описание", Task.Status.NEW);
        taskManager.addTask(task);

        assertEquals(task, taskManager.getTask(task.getId()), "Задача должна быть добавлена");
        taskManager.deleteTask(task.getId());
        assertNull(taskManager.getTask(task.getId()), "Задача должна быть удалена");
    }

    @Test
    public void testAddAndRemoveEpic() {
        Epic epic = new Epic(1, "Эпик 1", "Описание");
        taskManager.addEpic(epic);

        assertEquals(epic, taskManager.getEpic(epic.getId()), "Эпик должен быть добавлен");
        taskManager.removeEpic(epic.getId());
        assertNull(taskManager.getEpic(epic.getId()), "Эпик должен быть удален");
    }

    @Test
    public void testAddAndRemoveSubtask() {
        Epic epic = new Epic(1, "Эпик 1", "Описание");
        taskManager.addEpic(epic);

        Subtask subtask = new Subtask(2, "Подзадача 1", "Описание", Task.Status.NEW, epic.getId());
        taskManager.addSubtask(subtask);

        assertEquals(subtask, taskManager.getSubtask(subtask.getId()), "Подзадача должна быть добавлена");
        taskManager.removeSubtask(subtask.getId());
        assertNull(taskManager.getSubtask(subtask.getId()), "Подзадача должна быть удалена");
    }

}