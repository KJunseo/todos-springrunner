package practice.sr.todoapp.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.sr.todoapp.security.UserSession;
import practice.sr.todoapp.security.UserSessionRepository;
import practice.sr.todoapp.web.model.UserProfile;

import java.util.Objects;

@RestController
public class UserRestController {

    private UserSessionRepository sessionRepository;

    public UserRestController(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/api/user/profile")
    public ResponseEntity<UserProfile> userProfile() {
        UserSession userSession = sessionRepository.get();
        if(Objects.nonNull(userSession)) {
            return ResponseEntity.ok(new UserProfile(userSession.getUser()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
