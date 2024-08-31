package ru.yandex.javacourse.schedule.manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id;
    private String name;
    private String description;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(int id, String name, String description, Status status) {
        this(id, name, description, status, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null || duration == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    @Override
    public String toString() {
        return id + "," + name + "," + description + "," + status + "," +
                (duration != null ? duration.toMinutes() : "null") + "," +
                (startTime != null ? startTime.toString() : "null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status &&
                Objects.equals(duration, task.duration) &&
                Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, duration, startTime);
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Неверный формат строки задачи");
        }
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String description = parts[2];
        Status status = Status.valueOf(parts[3]);
        Duration duration = parts[4].equals("null") ? null : Duration.ofMinutes(Long.parseLong(parts[4]));
        LocalDateTime startTime = parts[5].equals("null") ? null : LocalDateTime.parse(parts[5]);
        return new Task(id, name, description, status, duration, startTime);
    }

    public enum Status {
        NEW,
        IN_PROGRESS,
        DONE
    }
}