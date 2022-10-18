package com.github.atomfrede.htmx_workshop.todo_item;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoItemNotFoundException extends RuntimeException{

    public TodoItemNotFoundException(Long id) {
        super(String.format("TodoItem with id %s not found", id));
    }
}
