package zip.hyeon.javaopenmission8.adapter.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig<S extends Session> {

    private final FindByIndexNameSessionRepository<S> sessionRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);
        http// 세션 설정
                .sessionManagement(sessionManagement -> sessionManagement
                        .invalidSessionUrl("/invalid")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/expired")
                        .sessionRegistry(sessionRegistry())
                );
        http// OAuth2 로그인
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .authorizationEndpoint(authorization -> authorization.baseUri("/login"))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/login/code/{registrationId}"))
                        .userInfoEndpoint(userinfo -> userinfo.userService(customOAuth2UserService()))
                        .failureHandler(customFailureHandler())
                        .successHandler(customSuccessHandler()));
        http// 인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/home", "/error", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

    @Bean
    public CustomFailureHandler customFailureHandler() {
        return new CustomFailureHandler();
    }

    @Bean
    public CustomSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }
}


