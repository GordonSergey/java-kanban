import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    @Test
    public void addAndRetrieveTasks() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task(1, "Task 1", "Description", Task.Status.NEW);
        Epic epic = new Epic(2, "Epic 1", "Description");
        Subtask subtask = new Subtask(3, "Subtask 1", "Description", Task.Status.IN_PROGRESS, 2);

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(task, taskManager.getTask(task.getId()), "Задача не найдена");
        assertEquals(epic, taskManager.getEpic(epic.getId()), "Эпик не найден");
        assertEquals(subtask, taskManager.getSubtask(subtask.getId()), "Подзадача не найдена");
    }
}