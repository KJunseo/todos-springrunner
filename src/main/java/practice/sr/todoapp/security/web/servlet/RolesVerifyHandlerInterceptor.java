package practice.sr.todoapp.security.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import practice.sr.todoapp.security.AccessDeniedException;
import practice.sr.todoapp.security.UnauthorizedAccessException;
import practice.sr.todoapp.security.UserSession;
import practice.sr.todoapp.security.UserSessionRepository;
import practice.sr.todoapp.security.support.RolesAllowedSupport;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Role(역할) 기반으로 사용자가 사용 권한을 확인하는 인터셉터 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class RolesVerifyHandlerInterceptor implements HandlerInterceptor, RolesAllowedSupport {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // allowed를 얻어오는 코드를 밖으로 분리
        RolesAllowed rolesAllowed = getRolesAllowed(handler);

        if(Objects.nonNull(rolesAllowed)) {

            // 로그인 되어 있는 지 확인
            if(Objects.isNull(request.getUserPrincipal())) {
                throw new UnauthorizedAccessException();
            }

            // 권한이 적절한지 확인
            Set<String> matchedRoles = Stream.of(rolesAllowed.value()).filter(request::isUserInRole).collect(Collectors.toSet());

            if(matchedRoles.isEmpty()) {
                throw new AccessDeniedException();
            }
        }

        return true;
    }
}
