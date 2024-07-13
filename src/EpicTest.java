import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {

    @Test
    public void testAddEpicAsSubtaskToItself() {
        Epic epic = new Epic(1, "Эпик 1", "Описание эпика");
        Subtask subtask = new Subtask(1, "Подзадача", "Описание подзадачи", Task.Status.NEW, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            epic.addSubtask(subtask);
        }, "Эпик не может быть добавлен как подзадача к самому себе.");

        assertEquals("Эпик не может быть добавлен как подзадача к самому себе.", exception.getMessage());
    }
}