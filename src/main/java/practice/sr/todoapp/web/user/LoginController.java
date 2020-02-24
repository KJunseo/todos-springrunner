package practice.sr.todoapp.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public String loginProcess(@Valid LoginCommand command, BindingResult bindingResult, Model model) {
        log.info("username: {}, password: {}", command.getUsername(), command.getPassword());

        // 매개변수를 Bean에 바인딩 할 때 발생할 수 있는 에러 확인
        // user이름을 4자 ~ 20자가 아닌 글자 수인 경우 에러 발생
        if(bindingResult.hasErrors()) {
            log.info("bindingResult: {}", bindingResult);
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("message", "사용자 값이 올바르지 않습니다.");
            return "login";
        }

        try {
            // 사용자 저장소에 사용자가 있는 경우, 비밀번호 일치 확인
            verifier.verify(command.getUsername(), command.getPassword());
        } catch (UserEntityNotFoundException error) {
            // 기존에 존재하는 사용자가 아닐 경우 가입 시키기
            joinder.join(command.getUsername(), command.getPassword());
        }

        return "redirect:/todos";
    }

    // 로직 분리
    // 비밀 번호가 다르면 오류 메세지 출력 후, login 화면으로 재이동
    @ExceptionHandler(UserPasswordNotMatchedException.class)
    public String handleUserPasswordNotMatchedException(UserPasswordNotMatchedException error, Model model) {
        model.addAttribute("message", "사용자 정보가 올바르지 않습니다");
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
