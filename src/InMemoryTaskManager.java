import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int currentId = 1;

    // TreeSet для хранения задач по приоритету
    private final NavigableSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));

    @Override
    public Task addTask(Task task) {
        if (!isTaskTimeOverlap(task)) {
            task.setId(generateId());
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task); // Добавляем задачу в TreeSet
            return task;
        } else {
            throw new IllegalArgumentException("Задачи пересекаются по времени.");
        }
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            prioritizedTasks.remove(tasks.get(task.getId())); // Удаляем старую задачу из TreeSet
            if (!isTaskTimeOverlap(task)) {
                tasks.put(task.getId(), task);
                prioritizedTasks.add(task); // Добавляем обновленную задачу в TreeSet
            } else {
                prioritizedTasks.add(tasks.get(task.getId())); // Возвращаем старую задачу в TreeSet
                throw new IllegalArgumentException("Задачи пересекаются по времени.");
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        Task task = tasks.remove(id);
        if (task != null) {
            prioritizedTasks.remove(task); // Удаляем задачу из TreeSet
            historyManager.remove(id);
        }
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Возвращает задачи по приоритету
    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public void removeAllTasks() {
        List<Integer> taskIds = new ArrayList<>(tasks.keySet());
        for (Integer taskId : taskIds) {
            historyManager.remove(taskId);
        }
        tasks.clear();
        prioritizedTasks.clear(); // Очищаем TreeSet
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            epic.updateStatus();
        }
    }

    @Override
    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : new ArrayList<>(epic.getSubtasks())) {
                subtasks.remove(subtask.getId());
                prioritizedTasks.remove(subtask); // Удаляем подзадачу из TreeSet
                historyManager.remove(subtask.getId());
            }
            historyManager.remove(id);
        }
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : new ArrayList<>(epics.values())) {
            removeEpic(epic.getId());
        }
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        if (!isTaskTimeOverlap(subtask)) {
            subtask.setId(generateId());
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.addSubtask(subtask);
                epic.updateStatus();
            }
            prioritizedTasks.add(subtask); // Добавляем подзадачу в TreeSet
            historyManager.add(subtask);
            return subtask;
        } else {
            throw new IllegalArgumentException("Подзадачи пересекаются по времени.");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            prioritizedTasks.remove(subtasks.get(subtask.getId())); // Удаляем старую подзадачу из TreeSet
            if (!isTaskTimeOverlap(subtask)) {
                subtasks.put(subtask.getId(), subtask);
                prioritizedTasks.add(subtask); // Добавляем обновленную подзадачу в TreeSet
                Epic epic = epics.get(subtask.getEpicId());
                if (epic != null) {
                    epic.updateStatus();
                }
            } else {
                prioritizedTasks.add(subtasks.get(subtask.getId())); // Возвращаем старую подзадачу в TreeSet
                throw new IllegalArgumentException("Подзадачи пересекаются по времени.");
            }
        }
    }

    @Override
    public void removeSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            prioritizedTasks.remove(subtask); // Удаляем подзадачу из TreeSet
            historyManager.remove(id);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(subtask);
                epic.updateStatus();
            }
        }
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Subtask> getSubtasksByEpic(int epicId) {
        List<Subtask> subtasksByEpic = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                subtasksByEpic.add(subtask);
            }
        }
        return subtasksByEpic;
    }

    @Override
    public void removeAllSubtasks() {
        for (Subtask subtask : new ArrayList<>(subtasks.values())) {
            removeSubtask(subtask.getId());
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private int generateId() {
        return currentId++;
    }

    // Метод для проверки пересечения задач
    private boolean isTaskTimeOverlap(Task newTask) {
        for (Task task : prioritizedTasks) {
            if (task.getStartTime() != null && task.getEndTime() != null &&
                    newTask.getStartTime() != null && newTask.getEndTime() != null) {
                if (isOverlap(task.getStartTime(), task.getEndTime(),
                        newTask.getStartTime(), newTask.getEndTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Метод для проверки пересечения двух временных интервалов
    private boolean isOverlap(LocalDateTime start1, LocalDateTime end1,
                              LocalDateTime start2, LocalDateTime end2) {
        return (start1.isBefore(end2) && end1.isAfter(start2));
    }
}