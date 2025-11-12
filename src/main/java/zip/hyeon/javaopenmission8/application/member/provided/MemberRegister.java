package zip.hyeon.javaopenmission8.application.member.provided;

import jakarta.validation.Valid;
import zip.hyeon.javaopenmission8.domain.member.Member;
import zip.hyeon.javaopenmission8.domain.member.MemberRegisterRequest;

public interface MemberRegister {
    Member registerOrUpdate(@Valid MemberRegisterRequest request);
}
