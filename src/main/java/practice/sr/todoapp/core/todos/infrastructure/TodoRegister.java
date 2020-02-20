package practice.sr.todoapp.core.todos.infrastructure;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import practice.sr.todoapp.core.todos.application.TodoEditor;

@Component
public class TodoRegister implements InitializingBean, CommandLineRunner, ApplicationRunner {

    private TodoEditor todoEditor;

    public TodoRegister(TodoEditor todoEditor) {
        this.todoEditor = todoEditor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        todoEditor.create("Task one!");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        todoEditor.create("Task two!");
    }

    @Override
    public void run(String... args) throws Exception {
        todoEditor.create("Task three!");
    }
}
