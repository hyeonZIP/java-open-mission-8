package zip.hyeon.javaopenmission8.domain.post;

import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zip.hyeon.javaopenmission8.domain.member.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String pullRequestUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MissionTag tag;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private int totalReviewCommentCount;

    private int totalReviewedMember;

    public static Post create(PostCreateRequest request) {
        Post post = new Post();

        post.member = requireNonNull(request.member());
        post.pullRequestUrl = requireNonNull(request.pullRequestUrl());
        post.tag = requireNonNull(request.tag());
        post.status = PostStatus.ACTIVATED;
        post.totalReviewCommentCount = 0;
        post.totalReviewedMember = 0;

        return post;
    }

    public void delete() {
        state(status != PostStatus.DELETED, "[ERROR] 이미 삭제된 게시글 입니다");

        status = PostStatus.DELETED;
    }

    public void activate() {
        state(status != PostStatus.ACTIVATED, "[ERROR] 이미 활성화된 게시글 입니다");

        status = PostStatus.ACTIVATED;
    }
}
