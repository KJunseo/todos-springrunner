package practice.sr.todoapp.core.todos.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.sr.todoapp.core.todos.domain.Todo;
import practice.sr.todoapp.core.todos.domain.TodoEntityNotFoundException;
import practice.sr.todoapp.core.todos.domain.TodoRepository;

import java.util.List;

/**
 * 할 일 검색기({@link TodoFinder}), 편집기(@{@link TodoEditor}) 인터페이스 구현체
 *
 * @author springrunner.kr@gmail.com
 */
@Service
@Transactional
public class TodoManager implements TodoFinder, TodoEditor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private TodoRepository todoRepository;

    public TodoManager(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    @Override
    public void create(String title) {
        todoRepository.save(Todo.create(title));
    }

    @Override
    public void update(Long id, String title, boolean completed) {
        Todo todo = todoRepository.findById(id)
                                  .orElseThrow(() -> new TodoEntityNotFoundException(id));

        todo.update(title, completed);
    }

    @Override
    public void delete(Long id) {
        Todo todo = todoRepository.findById(id)
                                  .orElseThrow(() -> new TodoEntityNotFoundException(id));

        todoRepository.delete(todo);
    }

}
