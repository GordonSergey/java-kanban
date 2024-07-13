public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, Status status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;

        if (id == epicId) {
            throw new IllegalStateException("Подзадача не может быть добавлена в качестве своего собственного эпика.");
        }
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;

        if (getId() == epicId) {
            throw new IllegalStateException("Подзадача не может быть добавлена в качестве своего собственного эпика.");
        }
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}