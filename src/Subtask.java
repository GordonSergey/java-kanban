import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, Status status, int epicId, Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, duration, startTime);
        if (id == epicId) {
            throw new IllegalStateException("Подзадача не может быть добавлена в качестве своего собственного эпика.");
        }
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, Status status, int epicId) {
        this(id, name, description, status, epicId, null, null);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public static Subtask fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String description = parts[2];
        Status status = Status.valueOf(parts[3]);
        int epicId = Integer.parseInt(parts[4]);
        Duration duration = Duration.ofMinutes(Long.parseLong(parts[5]));
        LocalDateTime startTime = LocalDateTime.parse(parts[6]);
        return new Subtask(id, name, description, status, epicId, duration, startTime);
    }

    @Override
    public String toString() {
        return getId() + "," + getName() + "," + getDescription() + "," + getStatus() + "," + epicId + "," +
                (getDuration() != null ? getDuration().toMinutes() : "null") + "," +
                (getStartTime() != null ? getStartTime() : "null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}