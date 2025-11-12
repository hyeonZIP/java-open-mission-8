package zip.hyeon.javaopenmission8.adapter.security.utils;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;
import zip.hyeon.javaopenmission8.adapter.security.dto.OAuth2UserConverterResponse;
import zip.hyeon.javaopenmission8.domain.member.Provider;

public class OAuth2UserConverter {
    private static final String GITHUB_PROVIDER_ID = "id";
    private static final String GITHUB_USERNAME = "login";
    private static final String GITHUB_PROFILE_IMAGE_URL = "avatar_url";

    public static OAuth2UserConverterResponse convert(String registrationId, OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if (registrationId.equals(Provider.GITHUB.getValue())) {
            return ofGithub(attributes);
        }

        throw new IllegalArgumentException("[ERROR] 유효하지 않은 registrationId 입니다 : " + registrationId);
    }

    private static OAuth2UserConverterResponse ofGithub(Map<String, Object> attributes) {
        String githubId = String.valueOf(attributes.get(GITHUB_PROVIDER_ID));
        String username = String.valueOf(attributes.get(GITHUB_USERNAME));
        String profileImageUrl = String.valueOf(attributes.get(GITHUB_PROFILE_IMAGE_URL));

        return new OAuth2UserConverterResponse(attributes, Provider.GITHUB, githubId, username, profileImageUrl);
    }
}
