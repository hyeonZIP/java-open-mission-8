package zip.hyeon.javaopenmission8.adapter.security.dto;

import java.util.Map;
import zip.hyeon.javaopenmission8.domain.member.Provider;

public record OAuth2UserConverterResponse(Map<String, Object> attributes, Provider provider, String providerId,
                                          String username) {
}
