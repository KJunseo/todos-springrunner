package practice.sr.todoapp.web.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import practice.sr.todoapp.core.user.application.ProfilePictureChanger;
import practice.sr.todoapp.core.user.domain.ProfilePicture;
import practice.sr.todoapp.core.user.domain.ProfilePictureStorage;
import practice.sr.todoapp.core.user.domain.User;
import practice.sr.todoapp.security.UserSession;
import practice.sr.todoapp.security.UserSessionRepository;
import practice.sr.todoapp.web.model.UserProfile;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RolesAllowed({"ROLE_USER"})
@RestController
public class UserRestController {

    private ProfilePictureChanger changer;
    private ProfilePictureStorage storage;
    private UserSessionRepository sessionRepository;

    public UserRestController(ProfilePictureChanger changer, ProfilePictureStorage storage, UserSessionRepository sessionRepository) {
        this.changer = changer;
        this.storage = storage;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/api/user/profile")
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }

    @PostMapping("/api/user/profile-picture")
    public UserProfile profilePictureChange(UserSession userSession, MultipartFile profilePicture) {
        // 프로필 이미지 변경
        URI profilePictureUri = storage.save(profilePicture.getResource());
        ProfilePicture newProfilePicture = new ProfilePicture(profilePictureUri);
        User savedUser = changer.change(userSession.getUser().getUsername(), newProfilePicture);

        // 세션 업데이트
        sessionRepository.set(new UserSession(savedUser));

        return new UserProfile(savedUser);
    }
}
