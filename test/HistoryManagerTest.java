import ru.yandex.javacourse.schedule.manager.HistoryManager;
import ru.yandex.javacourse.schedule.manager.InMemoryHistoryManager;
import ru.yandex.javacourse.schedule.manager.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {
    private HistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW);
        task2 = new Task(2, "Задача 2", "Описание 2", Task.Status.NEW);
        task3 = new Task(3, "Задача 3", "Описание 3", Task.Status.NEW);
    }

    @Test
    public void shouldReturnEmptyHistoryWhenNoTasksAdded() {
        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой, если задачи не были добавлены.");
    }

    @Test
    public void shouldAddTasksToHistoryWithoutDuplicates() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "История не должна содержать дублирующиеся задачи.");
        assertEquals(task2, history.get(0), "Задача 2 должна быть первой в истории после повторного добавления задачи 1.");
        assertEquals(task1, history.get(1), "Задача 1 должна быть последней в истории после повторного добавления.");
    }

    @Test
    public void shouldRemoveTaskFromBeginningOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task1.getId());

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "Размер истории должен быть 2 после удаления одной задачи.");
        assertFalse(history.contains(task1), "Задача 1 должна быть удалена из истории.");
        assertEquals(task2, history.get(0), "Задача 2 должна быть первой в истории после удаления.");
    }

    @Test
    public void shouldRemoveTaskFromMiddleOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task2.getId());

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "Размер истории должен быть 2 после удаления одной задачи.");
        assertFalse(history.contains(task2), "Задача 2 должна быть удалена из истории.");
        assertEquals(task1, history.get(0), "Задача 1 должна оставаться первой в истории.");
        assertEquals(task3, history.get(1), "Задача 3 должна быть последней в истории.");
    }

    @Test
    public void shouldRemoveTaskFromEndOfHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(task3.getId());

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "Размер истории должен быть 2 после удаления одной задачи.");
        assertFalse(history.contains(task3), "Задача 3 должна быть удалена из истории.");
        assertEquals(task1, history.get(0), "Задача 1 должна оставаться первой в истории.");
        assertEquals(task2, history.get(1), "Задача 2 должна быть последней в истории после удаления задачи 3.");
    }
}