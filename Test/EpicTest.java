import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {

    @Test
    public void testAddEpicAsSubtaskToItself() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            new Subtask(1, "Подзадача", "Описание подзадачи", Task.Status.NEW, 1);
        }, "Подзадача не может быть добавлена в качестве своего собственного эпика.");

        assertEquals("Подзадача не может быть добавлена в качестве своего собственного эпика.", exception.getMessage());
    }
}