package com.github.atomfrede.htmx_workshop;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.server.*;

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
        page.locator("[data-test=new-todo-input]").fill("htmx");
        page.locator("[data-test=new-todo-input]").press("Enter");
        page.waitForSelector("#list-item-10");
    }
}
