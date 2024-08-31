package ru.yandex.javacourse.schedule.http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.javacourse.schedule.manager.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager manager;
    private final Gson gson;

    public HistoryHandler(TaskManager manager) {
        this.manager = manager;
        this.gson = new Gson();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            if ("GET".equals(method)) {
                handleGetHistory(exchange);
            } else {
                sendServerError(exchange);
            }
        } catch (Exception e) {
            sendServerError(exchange);
        }
    }

    private void handleGetHistory(HttpExchange exchange) throws IOException {
        String response = gson.toJson(manager.getHistory());
        sendText(exchange, response);
    }
}