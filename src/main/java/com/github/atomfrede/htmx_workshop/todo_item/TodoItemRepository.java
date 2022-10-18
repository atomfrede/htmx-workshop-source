package com.gitlab.atomfrede.todomvc.thymeleaf.todo_item;

import com.gitlab.atomfrede.todomvc.thymeleaf.todo_item.web.TodoItemController;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {

    long nextId();
    int countAllByCompleted(boolean completed);

    List<TodoItem> findAllByCompleted(boolean completed);
}
