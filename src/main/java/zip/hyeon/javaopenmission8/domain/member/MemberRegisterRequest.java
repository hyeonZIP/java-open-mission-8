package zip.hyeon.javaopenmission8.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberRegisterRequest(
        @NotBlank String githubId,
        @NotBlank String username,
        @NotBlank @Email String email) {
}
