import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    @Test
    public void addAndRetrieveTasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task(1, "Задача 1", "Описание", Task.Status.NEW);
        Epic epic = new Epic(2, "Эпик 1", "Описание");
        Subtask subtask = new Subtask(3, "Подзадача 1", "Описание", Task.Status.IN_PROGRESS, 2);

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(task, taskManager.getTask(task.getId()), "Задача не найдена");
        assertEquals(epic, taskManager.getEpic(epic.getId()), "Эпик не найден");
        assertEquals(subtask, taskManager.getSubtask(subtask.getId()), "Подзадача не найдена");
    }
}