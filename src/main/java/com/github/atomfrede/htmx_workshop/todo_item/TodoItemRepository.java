package com.github.atomfrede.htmx_workshop.todo_item;

import org.springframework.data.repository.*;

import java.util.*;


@NoRepositoryBean
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {

    long nextId();
    int countAllByCompleted(boolean completed);

    List<TodoItem> findAllByCompleted(boolean completed);
}
