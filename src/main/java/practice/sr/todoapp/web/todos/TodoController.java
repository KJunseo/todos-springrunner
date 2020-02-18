package practice.sr.todoapp.web.todos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoController {

    @RequestMapping("/todos")
    public ModelAndView todos() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("todos"); // 접두사: classpath:/templates/
                                 // 접미사: .html
                                 // 따라서 setViewName을 todos로 주면, templates에 있는 todos.html을 뜻함
        return mv;
    }
}
