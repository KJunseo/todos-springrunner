package practice.sr.todoapp.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import practice.sr.todoapp.security.UserSessionRepository;

@Controller
public class LogoutController {

    UserSessionRepository sessionRepository;

    public LogoutController(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @RequestMapping("/logout")
    public String logout() {
        sessionRepository.clear();
        return "redirect:/todos";
    }
}
