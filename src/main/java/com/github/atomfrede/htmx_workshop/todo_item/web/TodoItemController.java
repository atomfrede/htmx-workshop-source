package com.github.atomfrede.htmx_workshop.todo_item.web;

import com.github.atomfrede.htmx_workshop.todo_item.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@Controller
@RequestMapping("/")
public class TodoItemController {

    private TodoItemRepository repository;

    public TodoItemController(TodoItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index(Model model) {
        addAttributesForIndex(model, ListFilter.ALL);
        return "index";
    }

    @GetMapping("/active")
    public String indexActive(Model model) {
        addAttributesForIndex(model, ListFilter.ACTIVE);
        return "index";
    }

    @GetMapping("/completed")
    public String indexCompleted(Model model) {
        addAttributesForIndex(model, ListFilter.COMPLETED);
        return "index";
    }

    private void addAttributesForIndex(Model model,
                                       ListFilter listFilter) {
        model.addAttribute("item", new TodoItemFormData());
        model.addAttribute("filter", listFilter);
        model.addAttribute("todos", getTodoItems(listFilter));
        model.addAttribute("totalNumberOfItems", repository.count());
        model.addAttribute("numberOfActiveItems", getNumberOfActiveItems());
        model.addAttribute("numberOfCompletedItems", getNumberOfCompletedItems());
    }

    @PostMapping
    public String addNewTodoItem(@Valid @ModelAttribute("item") TodoItemFormData formData) {
        repository.save(new TodoItem(repository.nextId(), formData.getTitle(), false));

        return "redirect:/";
    }

    @PutMapping("/{id}/toggle")
    public String toggleSelection(@PathVariable("id") Long id) {
        TodoItem todoItem = repository.findById(id)
                .orElseThrow(() -> new TodoItemNotFoundException(id));

        todoItem.setCompleted(!todoItem.isCompleted());
        repository.save(todoItem);
        return "redirect:/";
    }

    @PutMapping("/toggle-all")
    public String toggleAll() {
        Iterable<TodoItem> todoItems = repository.findAll();
        for (TodoItem todoItem : todoItems) {
            todoItem.setCompleted(!todoItem.isCompleted());
            repository.save(todoItem);
        }
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id) {
        repository.deleteById(id);

        return "redirect:/";
    }

    @DeleteMapping("/completed")
    public String deleteCompletedItems() {
        List<TodoItem> items = repository.findAllByCompleted(true);
        for (TodoItem item : items) {
            repository.deleteById(item.getId());
        }
        return "redirect:/";
    }

    private List<TodoItemDto> getTodoItems(ListFilter filter) {

        return switch (filter) {
            case ALL -> convertToDto(repository.findAll());
            case ACTIVE -> convertToDto(repository.findAllByCompleted(false));
            case COMPLETED -> convertToDto(repository.findAllByCompleted(true));
        };
    }

    private List<TodoItemDto> convertToDto(Iterable<TodoItem> todoItems) {
        List<TodoItemDto> results = new ArrayList<>();
        todoItems.forEach(it -> {
            TodoItemDto todoItemDto = new TodoItemDto(it.getId(), it.getTitle(), it.isCompleted());
            results.add(todoItemDto);
        });
        return results;
    }

    private int getNumberOfActiveItems() {
        return repository.countAllByCompleted(false);
    }

    private int getNumberOfCompletedItems() {
        return repository.countAllByCompleted(true);
    }

    public static record TodoItemDto(long id, String title, boolean completed) {
    }

    public enum ListFilter {
        ALL,
        ACTIVE,
        COMPLETED
    }
}
