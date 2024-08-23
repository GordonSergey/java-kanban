import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {

    private File tempFile;
    private FileBackedTaskManager manager;

    @BeforeEach
    public void setUp() throws IOException {
        // Создание временного файла
        tempFile = File.createTempFile("tasks", ".csv");
        tempFile.deleteOnExit();
        manager = new FileBackedTaskManager(tempFile);
    }

    @AfterEach
    public void tearDown() {
        // Удаление временного файла
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testSaveAndLoadEmptyFile() {
        manager.save();
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверка, что загруженный менеджер пустой
        assertTrue(loadedManager.getAllTasks().isEmpty(), "Менеджер должен быть пустым");
        assertTrue(loadedManager.getHistory().isEmpty(), "История должна быть пустой");
    }

    @Test
    public void testSaveAndLoadSingleTask() {
        Task task = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW);
        manager.addTask(task);
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, loadedManager.getAllTasks().size(), "Менеджер должен содержать одну задачу");
        assertEquals(task, loadedManager.getAllTasks().get(0), "Задача должна быть первой в списке");
    }

    @Test
    public void testSaveAndLoadMultipleTasks() {
        Task task1 = new Task(1, "Задача 1", "Описание 1", Task.Status.NEW);
        Task task2 = new Task(2, "Задача 2", "Описание 2", Task.Status.IN_PROGRESS);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.save();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(2, loadedManager.getAllTasks().size(), "Менеджер должен содержать две задачи");
        assertTrue(loadedManager.getAllTasks().contains(task1), "Менеджер должен содержать первую задачу");
        assertTrue(loadedManager.getAllTasks().contains(task2), "Менеджер должен содержать вторую задачу");
    }
}
