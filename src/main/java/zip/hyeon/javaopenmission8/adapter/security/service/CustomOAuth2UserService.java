package zip.hyeon.javaopenmission8.adapter.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import zip.hyeon.javaopenmission8.adapter.security.detail.CustomOAuth2User;
import zip.hyeon.javaopenmission8.adapter.security.dto.OAuth2UserConverterResponse;
import zip.hyeon.javaopenmission8.adapter.security.utils.OAuth2UserConverter;
import zip.hyeon.javaopenmission8.application.member.provided.MemberRegister;
import zip.hyeon.javaopenmission8.domain.member.MemberRegisterRequest;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRegister memberRegister;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = extractRegistrationId(userRequest);

        OAuth2UserConverterResponse response = OAuth2UserConverter.convert(registrationId, oAuth2User);

        memberRegisterOrUpdate(response);

        return CustomOAuth2User.of(response);
    }

    private void memberRegisterOrUpdate(OAuth2UserConverterResponse response) {
        MemberRegisterRequest request = MemberRegisterRequest.of(response);

        memberRegister.registerOrUpdate(request);
    }

    private String extractRegistrationId(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration().getRegistrationId();
    }
}
