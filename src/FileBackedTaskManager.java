import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        loadTasksFromFile();
    }

    @Override
    public Task addTask(Task task) {
        Task newTask = super.addTask(task);
        save();
        return newTask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public Epic addEpic(Epic epic) {
        Epic newEpic = super.addEpic(epic);
        save();
        return newEpic;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        Subtask newSubtask = super.addSubtask(subtask);
        save();
        return newSubtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }

    protected void save() {
        try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {

            for (Task task : getAllTasks()) {
                writer.write(task.toString() + "\n");
            }

            for (Epic epic : getAllEpics()) {
                writer.write(epic.toString() + "\n");
                for (Subtask subtask : getSubtasksByEpic(epic.getId())) {
                    writer.write(subtask.toString() + "\n");
                }
            }

            writer.write("\n" + historyToString(getHistory()) + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных в файл: " + file.getName());
            e.printStackTrace();
        }
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                Task task = Task.fromString(line);
                if (task instanceof Epic) {
                    super.addEpic((Epic) task);
                } else if (task instanceof Subtask) {
                    super.addSubtask((Subtask) task);
                } else {
                    super.addTask(task);
                }
            }

            if (reader.ready()) {
                String historyLine = reader.readLine();
                if (historyLine != null) {
                    List<Integer> historyIds = historyFromString(historyLine);
                    for (int id : historyIds) {
                        if (getTask(id) != null) {
                            getHistory().add(getTask(id));
                        } else if (getEpic(id) != null) {
                            getHistory().add(getEpic(id));
                        } else if (getSubtask(id) != null) {
                            getHistory().add(getSubtask(id));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных из файла: " + file.getName());
            e.printStackTrace();
        }
    }

    private String historyToString(List<Task> history) {
        StringBuilder sb = new StringBuilder();
        for (Task task : history) {
            sb.append(task.getId()).append(",");
        }
        return sb.toString();
    }

    private List<Integer> historyFromString(String historyLine) {
        List<Integer> historyIds = new ArrayList<>();
        if (historyLine == null || historyLine.trim().isEmpty()) {
            return historyIds;
        }
        String[] ids = historyLine.split(",");
        for (String id : ids) {
            try {
                int taskId = Integer.parseInt(id.trim());
                historyIds.add(taskId);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка формата ID в истории: " + id);
            }
        }
        return historyIds;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        return new FileBackedTaskManager(file);
    }

    public static void main(String[] args) {
        FileBackedTaskManager manager = loadFromFile(new File("tasks.csv"));

    }
}