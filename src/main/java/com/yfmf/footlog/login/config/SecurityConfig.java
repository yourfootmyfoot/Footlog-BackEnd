package com.yfmf.footlog.login.config;

import com.yfmf.footlog.login.handler.OAuth2AuthenticationFailureHandler;
import com.yfmf.footlog.login.handler.OAuth2AuthenticationSuccessHandler;
import com.yfmf.footlog.login.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .cors(Customizer.withDefaults()) // CORS 적용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 미사용
                .formLogin(AbstractHttpConfigurer::disable) // 기존 로그인 미사용
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                                request
                                        // 정적 리소스 허용
                                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.*").permitAll()

                                        // 로그인 페이지 허용
                                        .requestMatchers("/login").permitAll()

                                        // 이외 요청은 인증 필요
//                                .anyRequest().authenticated()

                                        // 임시 요청 허가
                                        .anyRequest().permitAll()
                )
                .oauth2Login(login ->
                        login
                                .authorizationEndpoint(authorization ->
                                        authorization
                                                // 로그인 시작 URI
                                                .baseUri("/oauth2/authorization"))
                                .redirectionEndpoint(redirect ->
                                        // 로그인 후 리다이렉션 URI
                                        redirect.baseUri("/login/oauth2/code/*"))
                                .userInfoEndpoint(userInfo ->
                                        // 사용자 정보 처리 서비스
                                        userInfo.userService(oAuth2UserService))
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler))
                .logout(logout ->
                        // 로그아웃 성공 URL
                        logout.logoutSuccessUrl("/login"));

        return http.build();
    }
}
