package practice.sr.todoapp.web.todos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import practice.sr.todoapp.core.todos.application.TodoEditor;
import practice.sr.todoapp.core.todos.application.TodoFinder;
import practice.sr.todoapp.core.todos.domain.Todo;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RolesAllowed({"ROLE_USER"})
@RequestMapping("/api/todos")
public class TodoRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private TodoFinder todoFinder;
    private TodoEditor todoEditor;

    public TodoRestController(TodoFinder todoFinder, TodoEditor todoEditor) {
        this.todoFinder = todoFinder;
        this.todoEditor = todoEditor;
    }

    @GetMapping
    public List<Todo> list() {
        return todoFinder.getAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody @Valid TodoWriteCommand command) {
        todoEditor.create(command.getTitle());

        log.info("command: {}", command.getTitle());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody TodoWriteCommand command) {
        todoEditor.update(id, command.getTitle(), command.isCompleted());

        log.info("id: {}, command: {}", id, command);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoEditor.delete(id);

        log.info("id: {}", id);
    }

    static class TodoWriteCommand {

        @Size(min = 4, max = 100)
        private String title;

        private boolean completed;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
