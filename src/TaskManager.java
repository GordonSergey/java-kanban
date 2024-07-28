import java.util.List;

public interface TaskManager {
    Task addTask(Task task);

    void updateTask(Task task);

    void deleteTask(int id);

    Task getTask(int id);

    List<Task> getAllTasks();

    void removeAllTasks();

    Epic addEpic(Epic epic);

    void updateEpic(Epic epic);

    void removeEpic(int id);

    Epic getEpic(int id);

    List<Epic> getAllEpics();

    void removeAllEpics();

    Subtask addSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    void removeSubtask(int id);

    Subtask getSubtask(int id);

    List<Subtask> getAllSubtasks();

    List<Subtask> getSubtasksByEpic(int epicId);

    void removeAllSubtasks();

    List<Task> getHistory();
}