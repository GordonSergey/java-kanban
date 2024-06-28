public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task(taskManager.generateId(), "Задача 1", "Описание задачи 1", Task.Status.NEW);
        Task task2 = new Task(taskManager.generateId(), "Задача 2", "Описание задачи 2", Task.Status.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task retrievedTask = taskManager.getTask(task1.getId());
        System.out.println(retrievedTask != null ? "Найденная задача: " + retrievedTask : "Задача с id " + task1.getId() + " не найдена.");

        Epic epic1 = new Epic(taskManager.generateId(), "Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic(taskManager.generateId(), "Эпик 2", "Описание эпика 2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask(taskManager.generateId(), "Подзадача 1", "Описание подзадачи 1", Task.Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask(taskManager.generateId(), "Подзадача 2", "Описание подзадачи 2", Task.Status.NEW, epic1.getId());
        Subtask subtask3 = new Subtask(taskManager.generateId(), "Подзадача 3", "Описание подзадачи 3", Task.Status.NEW, epic2.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        System.out.println("Все задачи: " + taskManager.getAllTasks());
        System.out.println("Все эпики: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи: " + taskManager.getAllSubtasks());

        task1.setStatus(Task.Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        subtask1.setStatus(Task.Status.DONE);
        taskManager.updateSubtask(subtask1);
        subtask2.setStatus(Task.Status.DONE);
        taskManager.updateSubtask(subtask2);

        System.out.println("Все задачи после обновления статусов: " + taskManager.getAllTasks());
        System.out.println("Все эпики после обновления статусов: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи после обновления статусов: " + taskManager.getAllSubtasks());

        taskManager.deleteTask(task2.getId());
        taskManager.removeEpic(epic1.getId());

        System.out.println("Все задачи после удаления: " + taskManager.getAllTasks());
        System.out.println("Все эпики после удаления: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи после удаления: " + taskManager.getAllSubtasks());
    }
}