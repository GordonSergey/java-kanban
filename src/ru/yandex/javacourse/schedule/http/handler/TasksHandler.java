package ru.yandex.javacourse.schedule.http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.javacourse.schedule.manager.Task;
import ru.yandex.javacourse.schedule.manager.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    private final Gson gson;

    public TasksHandler(TaskManager manager) {
        this.manager = manager;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    handleGetTasks(exchange);
                    break;
                case "POST":
                    handlePostTask(exchange);
                    break;
                case "DELETE":
                    handleDeleteTask(exchange);
                    break;
                default:
                    sendServerError(exchange);
            }
        } catch (Exception e) {
            sendServerError(exchange);
        }
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {

        String response = gson.toJson(manager.getAllTasks());
        sendText(exchange, response);
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        Task task = gson.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), Task.class);
        manager.addTask(task);
        exchange.sendResponseHeaders(201, -1);
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            int id = Integer.parseInt(query.substring(3));

            manager.removeTaskById(id);
            exchange.sendResponseHeaders(200, -1);
        } else {
            sendNotFound(exchange);
        }
    }
}