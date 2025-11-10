package zip.hyeon.javaopenmission8.domain.member;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {
    @Test
    void validEmail() {
        assertThatCode(() -> new Email("valid@naver.com")).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "invalid@naver", "invalidnaver.com", "@naver.com"})
    @NullAndEmptySource
    void invalidEmail(String email) {
        assertThatThrownBy(() -> new Email(email)).isInstanceOf(IllegalArgumentException.class);
    }
}