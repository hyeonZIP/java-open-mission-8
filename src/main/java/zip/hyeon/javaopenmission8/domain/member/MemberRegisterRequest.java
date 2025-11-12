package zip.hyeon.javaopenmission8.domain.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import zip.hyeon.javaopenmission8.adapter.security.dto.OAuth2UserConverterResponse;

public record MemberRegisterRequest(
        @NotNull Provider provider,
        @NotBlank String providerId,
        @NotBlank String username) {

    public static MemberRegisterRequest of(OAuth2UserConverterResponse response) {
        return new MemberRegisterRequest(
                response.provider(),
                response.providerId(),
                response.username()
        );
    }
}
