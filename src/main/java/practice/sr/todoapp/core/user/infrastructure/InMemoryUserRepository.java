package practice.sr.todoapp.core.user.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import practice.sr.todoapp.Constant;
import practice.sr.todoapp.core.user.domain.PasswordEncoder;
import practice.sr.todoapp.core.user.domain.User;
import practice.sr.todoapp.core.user.domain.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 메모리 기반 사용자 저장소 구현체
 *
 * @author springrunner.kr@gmail.com
 */
@Profile(Constant.PROFILE_DEVELOPMENT)
@Repository
public class InMemoryUserRepository implements UserRepository, ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<User> users = new CopyOnWriteArrayList<>();

    private PasswordEncoder passwordEncoder;

    public InMemoryUserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream().filter(user -> Objects.equals(user.getUsername(), username)).findAny();
    }

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = save(new User("user", passwordEncoder.encode("password")));
        log.info("신규 사용자를 등록합니다. (username: user, password: password)");
    }

}
