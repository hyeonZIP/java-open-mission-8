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

    public static Member register(String githubId, String username, String email) {
        Member member = new Member();

        member.githubId = requireNonNull(githubId);
        member.username = requireNonNull(username);
        member.email = new Email(email);
        member.status = MemberStatus.PENDING;

        return member;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVATED;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVATED, "ACTIVATED 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }
}
