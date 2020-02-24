package practice.sr.todoapp.security.web.method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import practice.sr.todoapp.security.UserSession;
import practice.sr.todoapp.security.UserSessionRepository;

// 컨트롤러에서 파라미터를 바인딩
public class UserSessionHandlerMethodArgunmentResolver implements HandlerMethodArgumentResolver {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserSessionRepository sessionRepository;

    public UserSessionHandlerMethodArgunmentResolver(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // 바인딩할 클래스 확인
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(UserSession.class);
    }

    // 바인딩 할 객체 조작
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        UserSession session = sessionRepository.get();
        log.info("User Session: {}", session);
        return session;
    }
}
