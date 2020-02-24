package practice.sr.todoapp.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import practice.sr.todoapp.core.user.application.UserJoinder;
import practice.sr.todoapp.core.user.application.UserPasswordVerifier;
import practice.sr.todoapp.core.user.domain.UserEntityNotFoundException;
import practice.sr.todoapp.core.user.domain.UserPasswordNotMatchedException;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    private UserPasswordVerifier verifier;

    private UserJoinder joinder;

    public LoginController(UserPasswordVerifier verifier, UserJoinder joinder) {
        this.verifier = verifier;
        this.joinder = joinder;
    }

    @GetMapping("/login")
    public void loginForm() {

    }

    @PostMapping("/login")
    public String loginProcess(@Valid LoginCommand command, Model model) {
        log.info("username: {}, password: {}", command.getUsername(), command.getPassword());

        try {
            // 사용자 저장소에 사용자가 있는 경우, 비밀번호 일치 확인
            verifier.verify(command.getUsername(), command.getPassword());
        } catch (UserPasswordNotMatchedException error) {
            // 비밀 번호가 다르면 오류 메세지 출력 후, login 화면으로 재이동
            model.addAttribute("message", error.getMessage());
            return "login";
        } catch (UserEntityNotFoundException error) {
            // 기존에 존재하는 사용자가 아닐 경우 가입 시키기
            joinder.join(command.getUsername(), command.getPassword());
        }

        return "redirect:/todos";
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
