package practice.sr.todoapp.web.todos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import practice.sr.todoapp.web.model.SiteProperties;

@Controller
public class TodoController {

    private SiteProperties siteProperties;

    public TodoController(SiteProperties siteProperties) {
        this.siteProperties = siteProperties; // 생성자를 통한 의존성 주입
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
}
