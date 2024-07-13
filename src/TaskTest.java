import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    void taskEqualityById() {
        Task task1 = new Task(1, "Task 1", "Description", Task.Status.NEW);
        Task task2 = new Task(1, "Task 2", "Description", Task.Status.IN_PROGRESS);

        assertEquals(task1.getId(), task2.getId(), "Задачи должны быть равны по id");
    }

    @Test
    void taskInheritanceEqualityById() {
        Epic epic1 = new Epic(1, "Epic 1", "Description");
        Epic epic2 = new Epic(1, "Epic 2", "Description");

        assertEquals(epic1.getId(), epic2.getId(), "Наследники класса Task должны быть равны по id");
    }
}