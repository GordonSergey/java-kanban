package ru.yandex.javacourse.schedule.http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.javacourse.schedule.manager.Epic;
import ru.yandex.javacourse.schedule.manager.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    private final Gson gson;

    public EpicsHandler(TaskManager manager) {
        this.manager = manager;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET":
                    handleGetEpics(exchange);
                    break;
                case "POST":
                    handlePostEpic(exchange);
                    break;
                case "DELETE":
                    handleDeleteEpic(exchange);
                    break;
                default:
                    sendServerError(exchange);
            }
        } catch (Exception e) {
            sendServerError(exchange);
        }
    }

    private void handleGetEpics(HttpExchange exchange) throws IOException {
        String response = gson.toJson(manager.getEpics());
        sendText(exchange, response);
    }

    private void handlePostEpic(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        Epic epic = gson.fromJson(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), Epic.class);
        manager.addEpic(epic);
        exchange.sendResponseHeaders(201, -1);
    }

    private void handleDeleteEpic(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query != null && query.startsWith("id=")) {
            int id = Integer.parseInt(query.substring(3));
            manager.removeEpic(id);
            exchange.sendResponseHeaders(200, -1);
        } else {
            sendNotFound(exchange);
        }
    }
}