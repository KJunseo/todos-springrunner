package practice.sr.todoapp.core.user.domain;

import javax.persistence.Embeddable;
import java.net.URI;

/**
 * 사용자 프로필 이미지 값 객체(Value Object)
 *
 * @author springrunner.kr@gmail.com
 */
@Embeddable
public class ProfilePicture {

    private URI uri;

    // for hibernate
    private ProfilePicture() { }

    public ProfilePicture(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return uri;
    }

    private void setUri(URI uri) {
        this.uri = uri;
    }

}