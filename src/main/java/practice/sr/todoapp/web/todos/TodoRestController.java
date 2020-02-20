package practice.sr.todoapp.web.todos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.sr.todoapp.core.todos.application.TodoFinder;
import practice.sr.todoapp.core.todos.domain.Todo;

import java.util.List;

@RestController
public class TodoRestController {

    private TodoFinder todoFinder;

    public TodoRestController(TodoFinder todoFinder) {
        this.todoFinder = todoFinder;
    }

    @GetMapping("/api/todos")
    public List<Todo> list() {
        return todoFinder.getAll();
    }
}
