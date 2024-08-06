import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTasksWithSameIdAreEqual() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW);
        Task task2 = new Task(1, "Задача 2", "Описание 2", Task.Status.IN_PROGRESS);

        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть равны");
    }

    @Test
    public void testSubtasksWithSameIdAreEqual() {
        Subtask subtask1 = new Subtask(1, "Подзадача 1", "Описание 1", Task.Status.NEW, 100);
        Subtask subtask2 = new Subtask(1, "Подзадача 2", "Описание 2", Task.Status.IN_PROGRESS, 100);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть равны");
    }

    @Test
    public void testEpicsWithSameIdAreEqual() {
        Epic epic1 = new Epic(1, "Эпик 1", "Описание 1");
        Epic epic2 = new Epic(1, "Эпик 2", "Описание 2");

        assertEquals(epic1, epic2, "Эпики с одинаковым ID должны быть равны");
    }
}