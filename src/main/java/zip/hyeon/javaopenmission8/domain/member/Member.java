package zip.hyeon.javaopenmission8.domain.member;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

import jakarta.persistence.Column;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private String username;

    private String profileImageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public static Member register(MemberRegisterRequest request) {
        Member member = new Member();

        member.provider = requireNonNull(request.provider());
        member.providerId = requireNonNull(request.providerId());
        member.username = requireNonNull(request.username());
        member.status = MemberStatus.ACTIVATED;
        member.role = MemberRole.ROLE_MEMBER;
        member.profileImageUrl = request.profileImageUrl();

        return member;
    }

    public void update(MemberRegisterRequest request) {
        state(status == MemberStatus.ACTIVATED, "[ERROR] ACTIVATED 상태가 아닙니다.");

        this.username = requireNonNull(request.username());
        this.profileImageUrl = request.profileImageUrl();
    }

    public void activate() {
        state(status != MemberStatus.ACTIVATED, "[ERROR] 이미 ACTIVATED 입니다.");

        this.status = MemberStatus.ACTIVATED;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVATED, "[ERROR] ACTIVATED 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
    }
}
