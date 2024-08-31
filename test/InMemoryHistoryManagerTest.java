import ru.yandex.javacourse.schedule.manager.InMemoryHistoryManager;
import ru.yandex.javacourse.schedule.manager.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testAddTask() {
        Task task = new Task(1, "Задача 1", "Описание", Task.Status.NEW);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task, history.get(0), "Задача должна быть первой в истории");
    }

    @Test
    public void testAddMultipleTasks() {
        Task task1 = new Task(1, "Задача 1", "Описание", Task.Status.NEW);
        Task task2 = new Task(2, "Задача 2", "Описание", Task.Status.IN_PROGRESS);
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи");
        assertEquals(task1, history.get(0), "Первая задача должна быть первой в истории");
        assertEquals(task2, history.get(1), "Вторая задача должна быть второй в истории");
    }

    @Test
    public void testGetHistoryReturnsCopy() {
        Task task = new Task(1, "Задача 1", "Описание", Task.Status.NEW);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        history.clear();

        history = historyManager.getHistory();
        assertEquals(1, history.size(), "Оригинальная история не должна изменяться");
        assertEquals(task, history.get(0), "Задача должна быть первой в истории");
    }

    @Test
    public void testAddAndRemoveHistory() {
        Task task = new Task(1, "Задача 1", "Описание", Task.Status.NEW);
        historyManager.add(task);

        assertTrue(historyManager.getHistory().contains(task), "История должна содержать задачу");

        historyManager.remove(task.getId());
        assertFalse(historyManager.getHistory().contains(task), "История не должна содержать задачу после удаления");
    }

    private void assertFalse(boolean contains, String s) {
    }

    @Test
    public void testAddTaskReplacesPreviousInHistory() {
        Task task1 = new Task(1, "Задача 1", "Описание", Task.Status.NEW);
        Task task2 = new Task(1, "Задача 2", "Описание", Task.Status.IN_PROGRESS);

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task2, history.get(0), "В истории должна быть только последняя версия задачи");
    }
}