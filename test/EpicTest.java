import ru.yandex.javacourse.schedule.manager.Epic;
import ru.yandex.javacourse.schedule.manager.Subtask;
import ru.yandex.javacourse.schedule.manager.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {

    @Test
    public void testAddEpicAsSubtaskToItself() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            new Subtask(1, "Подзадача", "Описание", Task.Status.NEW, 1);
        }, "Подзадача не может быть добавлена в качестве своего собственного эпика.");

        assertEquals("Подзадача не может быть добавлена в качестве своего собственного эпика.", exception.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        Epic epic1 = new Epic(1, "Эпик 1", "Описание");
        Epic epic2 = new Epic(1, "Эпик 1", "Описание");

        assertEquals(epic1, epic2, "Эпики с одинаковым ID должны быть равны");
        assertEquals(epic1.hashCode(), epic2.hashCode(), "Хэш-коды равных эпиков должны быть одинаковы");
    }

    @Test
    public void shouldHaveStatusNewWhenAllSubtasksAreNew() {
        Epic epic = new Epic(2, "Эпик задача", "Описание эпика");
        Subtask subtask1 = new Subtask(3, "Подзадача 1", "Описание 1", Task.Status.NEW, 2);
        Subtask subtask2 = new Subtask(4, "Подзадача 2", "Описание 2", Task.Status.NEW, 2);

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        assertEquals(Task.Status.NEW, epic.getStatus(), "Статус эпика должен быть NEW, когда все подзадачи NEW.");
    }

    @Test
    public void shouldHaveStatusDoneWhenAllSubtasksAreDone() {
        Epic epic = new Epic(5, "Эпик задача", "Описание эпика");
        Subtask subtask1 = new Subtask(6, "Подзадача 1", "Описание 1", Task.Status.DONE, 5);
        Subtask subtask2 = new Subtask(7, "Подзадача 2", "Описание 2", Task.Status.DONE, 5);

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        assertEquals(Task.Status.DONE, epic.getStatus(), "Статус эпика должен быть DONE, когда все подзадачи DONE.");
    }

    @Test
    public void shouldHaveStatusInProgressWhenSubtasksAreNewAndDone() {
        Epic epic = new Epic(8, "Эпик задача", "Описание эпика");
        Subtask subtask1 = new Subtask(9, "Подзадача 1", "Описание 1", Task.Status.NEW, 8);
        Subtask subtask2 = new Subtask(10, "Подзадача 2", "Описание 2", Task.Status.DONE, 8);

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        assertEquals(Task.Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, когда подзадачи имеют разные статусы.");
    }

    @Test
    public void shouldHaveStatusInProgressWhenAllSubtasksAreInProgress() {
        Epic epic = new Epic(11, "Эпик задача", "Описание эпика");
        Subtask subtask1 = new Subtask(12, "Подзадача 1", "Описание 1", Task.Status.IN_PROGRESS, 11);
        Subtask subtask2 = new Subtask(13, "Подзадача 2", "Описание 2", Task.Status.IN_PROGRESS, 11);

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        assertEquals(Task.Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, когда все подзадачи IN_PROGRESS.");
    }

    @Test
    public void shouldHaveStatusInProgressWhenSubtasksHaveMixedStatuses() {
        Epic epic = new Epic(14, "Эпик задача", "Описание эпика");
        Subtask subtask1 = new Subtask(15, "Подзадача 1", "Описание 1", Task.Status.NEW, 14);
        Subtask subtask2 = new Subtask(16, "Подзадача 2", "Описание 2", Task.Status.IN_PROGRESS, 14);

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        assertEquals(Task.Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, когда подзадачи имеют смешанные статусы.");
    }

    @Test
    public void shouldHaveStatusNewWhenNoSubtasksExist() {
        Epic epic = new Epic(17, "Эпик задача", "Описание эпика");

        assertEquals(Task.Status.NEW, epic.getStatus(), "Статус эпика должен быть NEW, когда нет подзадач.");
    }
}