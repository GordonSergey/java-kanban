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
}