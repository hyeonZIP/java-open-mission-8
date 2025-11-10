package zip.hyeon.javaopenmission8.domain.member;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String githubId;

    @Column(nullable = false)
    private String username;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public static Member register(MemberRegisterRequest request) {
        Member member = new Member();

        member.githubId = requireNonNull(request.githubId());
        member.username = requireNonNull(request.username());
        member.email = new Email(request.email());
        member.status = MemberStatus.ACTIVATED;

        return member;
    }

    public void activate() {
        state(status != MemberStatus.ACTIVATED, "이미 ACTIVATED 입니다.");

        this.status = MemberStatus.ACTIVATED;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVATED, "ACTIVATED 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }
}
