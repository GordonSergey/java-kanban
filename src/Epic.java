import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW);
        this.subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void updateStatus() {
        boolean allDone = true;
        boolean allNew = true;

        for (Subtask subtask : subtasks) {
            allDone = (subtask.getStatus() == Status.DONE) && allDone;
            allNew = (subtask.getStatus() == Status.NEW) && allNew;
        }

        setStatus(allDone ? Status.DONE : allNew ? Status.NEW : Status.IN_PROGRESS);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + subtasks +
                '}';
    }
}