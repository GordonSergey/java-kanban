package ru.yandex.javacourse.schedule.http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.javacourse.schedule.manager.Subtask;
import ru.yandex.javacourse.schedule.manager.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    private final Gson gson;

    public SubtasksHandler(TaskManager manager) {
        this.manager = manager;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    handleGetSubtasks(exchange);
                    break;
                case "POST":
                    handlePostSubtask(exchange);
                    break;
                case "DELETE":
                    handleDeleteSubtask(exchange);
                    break;
                default:
                    sendServerError(exchange);
            }
        } catch (Exception e) {
            sendServerError(exchange);
        }
    }

    private void handleGetSubtasks(HttpExchange exchange) throws IOException {

        String response = gson.toJson(manager.getAllSubtasks());
        sendText(exchange, response);
    }

    private void handlePostSubtask(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        Subtask subtask = gson.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), Subtask.class);
        manager.addSubtask(subtask);
        exchange.sendResponseHeaders(201, -1);
    }

    private void handleDeleteSubtask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            int id = Integer.parseInt(query.substring(3));
            manager.removeSubtask(id);
            exchange.sendResponseHeaders(200, -1);
        } else {
            sendNotFound(exchange);
        }
    }
}