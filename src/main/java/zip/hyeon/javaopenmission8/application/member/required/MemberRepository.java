package zip.hyeon.javaopenmission8.application.member.required;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import zip.hyeon.javaopenmission8.domain.member.Member;
import zip.hyeon.javaopenmission8.domain.member.Provider;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByProviderAndProviderId(Provider provider, String providerId);
}
