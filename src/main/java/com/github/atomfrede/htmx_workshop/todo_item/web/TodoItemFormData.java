package com.github.atomfrede.htmx_workshop.todo_item.web;

import javax.validation.constraints.*;

public class TodoItemFormData {

    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
