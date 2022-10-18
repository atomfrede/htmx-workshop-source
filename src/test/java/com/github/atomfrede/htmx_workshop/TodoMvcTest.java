package com.gitlab.atomfrede.todomvc.htmxplaywright;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoMvcTest extends PlaywrightSetup{

    @LocalServerPort
    private int port;

    private String baseUrl() {
        return "localhost:%s".formatted(port);
    }

    @Test
    void shouldAddNewTodo() {
        page.navigate(baseUrl());
        page.locator("#new-todo-input").fill("htmx");
        page.locator("#new-todo-input").press("Enter");
        page.waitForSelector("#list-item-10");
    }
}
