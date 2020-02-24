package practice.sr.todoapp.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public void loginForm() {

    }

    @PostMapping("/login")
    public String loginProcess(@Valid LoginCommand command) {
        log.info("username: {}, password: {}", command.getUsername(), command.getPassword());
        return "login";
    }

    static class LoginCommand {

        @Size(min = 4, max = 20)
        private String username;

        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
