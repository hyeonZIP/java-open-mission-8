package zip.hyeon.javaopenmission8.application.member;

import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import zip.hyeon.javaopenmission8.application.member.provided.MemberRegister;
import zip.hyeon.javaopenmission8.application.member.required.MemberRepository;
import zip.hyeon.javaopenmission8.domain.member.Member;
import zip.hyeon.javaopenmission8.domain.member.MemberRegisterRequest;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
    private final MemberRepository memberRepository;

    @Override
    public Member registerOrUpdate(@Valid MemberRegisterRequest request) {
        Member member = memberRepository
                .findByProviderAndProviderId(request.provider(), request.providerId())
                .map(existingMember -> updateExistingMember(existingMember, request))
                .orElseGet(() -> registerNewMember(request));

        return memberRepository.save(member);
    }

    private Member updateExistingMember(Member member, MemberRegisterRequest request) {
        member.update(request);
        return member;
    }

    private Member registerNewMember(MemberRegisterRequest request) {
        return Member.register(request);
    }
}
