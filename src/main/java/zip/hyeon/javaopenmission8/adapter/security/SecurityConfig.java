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
import zip.hyeon.javaopenmission8.adapter.security.handler.CustomSuccessHandler;
import zip.hyeon.javaopenmission8.adapter.security.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig<S extends Session> {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final FindByIndexNameSessionRepository<S> sessionRepository;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http// 비활성화 설정
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
        http// 세션 설정
                .sessionManagement(sessionManagement -> sessionManagement
                        .invalidSessionUrl("/home?sessionError=invalid")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/home?sessionError=expired")
                        .sessionRegistry(sessionRegistry()));
        http// OAuth2 로그인
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .authorizationEndpoint(authorization -> authorization.baseUri("/login"))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/login/code/{registrationId}"))
                        .userInfoEndpoint(userinfo -> userinfo.userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                        .permitAll());
        http// 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(request -> request.getMethod().equals("GET") &&
                                request.getRequestURI().equals("/logout"))
                        .logoutSuccessUrl("/home")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll());

        http// 인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }
}


