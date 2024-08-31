import ru.yandex.javacourse.schedule.manager.Epic;
import ru.yandex.javacourse.schedule.manager.Subtask;
import ru.yandex.javacourse.schedule.manager.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {

    private File tempFile;
    private FileBackedTaskManager manager;

    @BeforeEach
    public void setUp() throws IOException {

        tempFile = File.createTempFile("tasks", ".csv");
        tempFile.deleteOnExit();
        manager = new FileBackedTaskManager(tempFile);
    }

    @AfterEach
    public void tearDown() {

        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testSaveAndLoadEmptyFile() {
        manager.save();
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertTrue(loadedManager.getAllTasks().isEmpty(), "Менеджер должен быть пустым");
        assertTrue(loadedManager.getHistory().isEmpty(), "История должна быть пустой");
    }

    @Test
    public void testSaveAndLoadSingleTask() {
        Task task = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW, null, null);
        manager.addTask(task);
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, loadedManager.getAllTasks().size(), "Менеджер должен содержать одну задачу");
        assertEquals(task, loadedManager.getAllTasks().get(0), "Задача должна быть первой в списке");
    }

    @Test
    public void testSaveAndLoadMultipleTasks() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW, null, null);
        Task task2 = new Task(2, "Задача 2", "Описание 2", Task.Status.IN_PROGRESS, null, null);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(2, loadedManager.getAllTasks().size(), "Менеджер должен содержать две задачи");
        assertTrue(loadedManager.getAllTasks().contains(task1), "Менеджер должен содержать первую задачу");
        assertTrue(loadedManager.getAllTasks().contains(task2), "Менеджер должен содержать вторую задачу");
    }

    @Test
    public void testTaskOverlapDetection() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW, Duration.ofHours(2), LocalDateTime.of(2024, 8, 1, 10, 0));
        Task task2 = new Task(2, "Задача 2", "Описание 2", Task.Status.NEW, Duration.ofHours(2), LocalDateTime.of(2024, 8, 1, 11, 0));

        manager.addTask(task1);
        manager.save();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.addTask(task2);
            manager.save();
        }, "Задачи должны пересекаться по времени.");

        assertEquals("Задачи пересекаются по времени.", exception.getMessage());
    }

    @Test
    public void testRemoveAllTasks() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW);
        Task task2 = new Task(2, "Задача 2", "Описание 2", Task.Status.IN_PROGRESS);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.save();

        manager.removeAllTasks();
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertTrue(loadedManager.getAllTasks().isEmpty(), "Все задачи должны быть удалены.");
    }

    @Test
    public void testRemoveAllEpics() {
        Epic epic = new Epic(1, "Эпик 1", "Описание эпика");
        Subtask subtask1 = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", Task.Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask(3, "Подзадача 2", "Описание подзадачи 2", Task.Status.NEW, epic.getId());

        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.save();

        manager.removeAllEpics();
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertTrue(loadedManager.getAllEpics().isEmpty(), "Все эпики должны быть удалены.");
        assertTrue(loadedManager.getAllSubtasks().isEmpty(), "Подзадачи, связанные с удаленными эпиками, должны быть удалены.");
    }

    @Test
    public void testTaskHistory() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW);
        Task task2 = new Task(2, "Задача 2", "Описание 2", Task.Status.IN_PROGRESS);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        List<Task> history = loadedManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи.");
        assertTrue(history.contains(task1), "История должна содержать задачу 1.");
        assertTrue(history.contains(task2), "История должна содержать задачу 2.");
    }
}