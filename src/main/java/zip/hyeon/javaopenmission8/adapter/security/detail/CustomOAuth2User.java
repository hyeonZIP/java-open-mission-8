package zip.hyeon.javaopenmission8.adapter.security.detail;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import zip.hyeon.javaopenmission8.adapter.security.dto.OAuth2UserConverterResponse;
import zip.hyeon.javaopenmission8.domain.member.MemberRole;
import zip.hyeon.javaopenmission8.domain.member.Provider;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, Object> attributes;
    private final Provider provider;
    private final String providerId;
    private final String name;
    private final List<GrantedAuthority> authorities;

    public static CustomOAuth2User of(OAuth2UserConverterResponse response) {
        return new CustomOAuth2User(
                response.attributes(),
                response.provider(),
                response.providerId(),
                response.username(),
                List.of(new SimpleGrantedAuthority(MemberRole.ROLE_MEMBER.name())));
    }
}
