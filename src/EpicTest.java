import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {

    @Test
    public void testAddEpicAsSubtaskToItself() {
        Epic epic = new Epic(1, "Epic 1", "Epic Description");
        Subtask subtask = new Subtask(1, "Subtask", "Subtask Description", Task.Status.NEW, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            epic.addSubtask(subtask);
        }, "Epic cannot be added as a subtask to itself.");

        assertEquals("Epic cannot be added as a subtask to itself.", exception.getMessage());
    }
}