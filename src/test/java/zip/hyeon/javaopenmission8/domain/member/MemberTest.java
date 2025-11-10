package zip.hyeon.javaopenmission8.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {
    Member member;

    @BeforeEach
    void setUp() {
        member = Member.register("12345", "hyeonZIP", "abcde@naver.com");
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void activate() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVATED);
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(() -> member.activate()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivated() {
        member.activate();
        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivatedFail() {
        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
    }
}