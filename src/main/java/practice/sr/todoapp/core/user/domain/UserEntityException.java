package practice.sr.todoapp.core.user.domain;

import practice.sr.todoapp.commons.SystemException;

/**
 * 사용자 엔티티에서 발생 가능한 최상위 예외 클래스
 *
 * @author springrunner.kr@gmail.com
 */
public class UserEntityException extends SystemException {

    public UserEntityException(String format, Object... args) {
        super(format, args);
    }

    public UserEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}