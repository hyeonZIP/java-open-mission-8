package zip.hyeon.javaopenmission8.domain.member;

import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
public record Email(String email) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public Email {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일이 유효하지 않습니다 : " + email);
        }
    }
}
