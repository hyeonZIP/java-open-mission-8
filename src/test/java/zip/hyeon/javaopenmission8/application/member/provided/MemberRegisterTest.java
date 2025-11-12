package zip.hyeon.javaopenmission8.application.member.provided;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zip.hyeon.javaopenmission8.application.member.MemberModifyService;
import zip.hyeon.javaopenmission8.application.member.required.MemberRepository;
import zip.hyeon.javaopenmission8.domain.member.Member;
import zip.hyeon.javaopenmission8.domain.member.MemberFixture;

class MemberRegisterTest {
    MemberRepository memberRepository;
    MemberRegister memberRegister;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        memberRegister = new MemberModifyService(memberRepository);
    }

    @Test
    void registerNewMember() {
        var request = MemberFixture.createMemberRegisterRequest();

        given(memberRepository.findByProviderAndProviderId(request.provider(), request.providerId())).willReturn(
                Optional.empty());
        given(memberRepository.save(any(Member.class))).willAnswer(invocation -> invocation.getArgument(0));

        Member member = memberRegister.registerOrUpdate(request);

        assertThat(member.getProvider()).isEqualTo(request.provider());
        assertThat(member.getProviderId()).isEqualTo(request.providerId());
        assertThat(member.getUsername()).isEqualTo(request.username());
        assertThat(member.getProfileImageUrl()).isEqualTo(request.profileImageUrl());
    }

    @Test
    void updateExistingMember() {
        Member existingMember = Member.register(MemberFixture.createMemberRegisterRequest());
        var updateRequest = MemberFixture.createMemberRegisterRequest("updateUsername", "updateProfileImageUrl");

        given(memberRepository.findByProviderAndProviderId(updateRequest.provider(),
                updateRequest.providerId())).willReturn(Optional.of(existingMember));
        given(memberRepository.save(any(Member.class))).willAnswer(invocation -> invocation.getArgument(0));

        Member updatedMember = memberRegister.registerOrUpdate(updateRequest);

        assertThat(updatedMember).isSameAs(existingMember);
        assertThat(existingMember.getProvider()).isEqualTo(updateRequest.provider());
        assertThat(existingMember.getProviderId()).isEqualTo(updateRequest.providerId());
        assertThat(updatedMember.getUsername()).isEqualTo(updateRequest.username());
        assertThat(updatedMember.getProfileImageUrl()).isEqualTo(updateRequest.profileImageUrl());
    }
}