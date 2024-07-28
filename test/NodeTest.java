import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    void testNodeCreation() {
        Task task = new Task(1, "Задача 1", "Описание задачи 1", Task.Status.NEW);
        Node<Task> node = new Node<>(task);

        assertEquals(task, node.getData(), "Задача в узле не соответствует ожидаемой");
    }

    @Test
    void testNodeLinking() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", Task.Status.NEW);
        Task task2 = new Task(2, "Задача 2", "Описание задачи 2", Task.Status.IN_PROGRESS);
        Node<Task> node1 = new Node<>(task1);
        Node<Task> node2 = new Node<>(task2);

        // Связываем узлы
        node1.setNext(node2);
        node2.setPrev(node1);

        assertEquals(node2, node1.getNext(), "Следующий узел не соответствует ожидаемому");
        assertEquals(node1, node2.getPrev(), "Предыдущий узел не соответствует ожидаемому");
    }

    @Test
    void testNodeNullLinking() {
        Task task = new Task(1, "Задача 1", "Описание задачи 1", Task.Status.NEW);
        Node<Task> node = new Node<>(task);

        // Связываем узлы с null
        node.setNext(null);
        node.setPrev(null);

        assertNull(node.getNext(), "Следующий узел должен быть null");
        assertNull(node.getPrev(), "Предыдущий узел должен быть null");
    }

    @Test
    void testNodeDataUpdate() {
        Task task1 = new Task(1, "Задача 1", "Описание задачи 1", Task.Status.NEW);
        Task task2 = new Task(2, "Задача 2", "Описание задачи 2", Task.Status.IN_PROGRESS);
        Node<Task> node = new Node<>(task1);

        // Обновляем данные узла
        node.setData(task2);

        assertEquals(task2, node.getData(), "Задача в узле не была обновлена корректно");
    }
}