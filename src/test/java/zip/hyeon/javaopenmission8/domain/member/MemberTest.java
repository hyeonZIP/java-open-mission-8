package zip.hyeon.javaopenmission8.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {
    Member member;

    @BeforeEach
    void setUp() {
        member = Member.register(MemberFixture.createMemberRegisterRequest());
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVATED);
    }

    @Test
    void activate() {
        member.deactivate();
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVATED);
    }

    @Test
    void activateFail() {
        assertThatThrownBy(() -> member.activate()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivated() {
        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivatedFail() {
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void updateMember() {
        Provider previousProvider = member.getProvider();
        String providerId = member.getProviderId();

        MemberRegisterRequest updateRequest = MemberFixture.createMemberRegisterRequest("updateUsername",
                "updateImage");

        member.update(updateRequest);

        assertThat(member.getUsername()).isEqualTo(updateRequest.username());
        assertThat(member.getProfileImageUrl()).isEqualTo(updateRequest.profileImageUrl());
        assertThat(previousProvider).isEqualTo(member.getProvider());
        assertThat(providerId).isEqualTo(member.getProviderId());
    }
}