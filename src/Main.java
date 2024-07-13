public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task(0, "Задача 1", "Описание задачи 1", Task.Status.NEW);
        Task task2 = new Task(0, "Задача 2", "Описание задачи 2", Task.Status.NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task retrievedTask = taskManager.getTask(task1.getId());
        System.out.println(retrievedTask != null ? "Найденная задача: " + retrievedTask : "Задача с id " + task1.getId() + " не найдена.");

        Epic epic1 = new Epic(0, "Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic(0, "Эпик 2", "Описание эпика 2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask(0, "Подзадача 1", "Описание подзадачи 1", Task.Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask(0, "Подзадача 2", "Описание подзадачи 2", Task.Status.NEW, epic1.getId());
        Subtask subtask3 = new Subtask(0, "Подзадача 3", "Описание подзадачи 3", Task.Status.NEW, epic2.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        printAllTasks(taskManager);

        taskManager.getTask(task1.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getEpic(epic2.getId());

        printAllTasks(taskManager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Subtask subtask : manager.getSubtasksByEpic(epic.getId())) {
                System.out.println("--> " + subtask);
            }
        }

        System.out.println("Подзадачи:");
        for (Subtask subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}