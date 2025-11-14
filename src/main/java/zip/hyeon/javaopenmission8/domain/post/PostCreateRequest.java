package zip.hyeon.javaopenmission8.domain.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import zip.hyeon.javaopenmission8.domain.member.Member;

public record PostCreateRequest(
        @NotNull Member member,
        @NotBlank @URL String pullRequestUrl,
        @NotNull MissionTag tag) {
}
