import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private int currentId =1;

    public int generateId() {
        return currentId++;
    }

    public Task addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Map<Integer, Task> getAllTasks() {
        return tasks;
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void removeEpic(int id) {
        epics.remove(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Map<Integer, Epic> getAllEpics() {
        return epics;
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubtask(subtask);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).updateStatus();
    }

    public void removeSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            epics.get(subtask.getEpicId()).removeSubtask(subtask);
            epics.get(subtask.getEpicId()).updateStatus();
        }
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Map<Integer, Subtask> getAllSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subtasks=" + subtasks +
                '}';
    }
}