import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubtaskTest {
    @Test
    void testSetEpicIdCannotBeSameAsId() {
        assertThrows(IllegalStateException.class, () -> {
            new Subtask(1, "Подзадача 1", "Описание подзадачи 1", Task.Status.NEW, 1);
        }, "Подзадача была неправильно принята как связанная с собственным эпиком.");
    }
}