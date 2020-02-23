package practice.sr.todoapp.web.todos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import practice.sr.todoapp.commons.domain.Spreadsheet;
import practice.sr.todoapp.core.todos.application.TodoFinder;
import practice.sr.todoapp.core.todos.domain.Todo;
import practice.sr.todoapp.web.convert.TodoToSpreadsheetConverter;
import practice.sr.todoapp.web.model.SiteProperties;

import java.util.List;

@Controller
public class TodoController {

    private SiteProperties siteProperties;

    private TodoFinder todoFinder;

    public TodoController(SiteProperties siteProperties, TodoFinder todoFinder) {
        this.siteProperties = siteProperties; // 생성자를 통한 의존성 주입
        this.todoFinder = todoFinder;
    }

    @RequestMapping("/todos")
    public ModelAndView todos() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("todos"); // 접두사: classpath:/templates/
                                 // 접미사: .html
                                 // 따라서 setViewName을 todos로 주면, templates에 있는 todos.html을 뜻함
        mv.addObject("site", siteProperties);
        return mv;
    }

    @RequestMapping(value = "/todos", produces = "text/csv")
    public ModelAndView downloadTodos() {
        List<Todo> todos = todoFinder.getAll();
        Spreadsheet spreadsheet = new TodoToSpreadsheetConverter().convert(todos);

        ModelAndView mv = new ModelAndView();
        mv.addObject(spreadsheet);

        return mv;
    }
}
