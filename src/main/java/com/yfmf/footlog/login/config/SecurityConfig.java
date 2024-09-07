package com.yfmf.footlog.login.config;

import com.yfmf.footlog.login.handler.CustomSuccessHandler;
import com.yfmf.footlog.login.service.CustomOAuth2UserService;
import com.yfmf.footlog.login.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // csrf disable
                .csrf(AbstractHttpConfigurer::disable)
                // From 로그인 방식 disable
                .formLogin(formLogin ->
                        formLogin.loginPage("/login"))
                // HTTP Basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)
                // oauth2
                .oauth2Login(auth ->
                        auth.userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(customOAuth2UserService))
                                .successHandler(customSuccessHandler))
                // 경로별 인가 작업
                .authorizeHttpRequests(auth ->
                        auth
                                .anyRequest().permitAll())
//                                .requestMatchers("/", "/login").permitAll()
//                                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.*").permitAll())
//                                .anyRequest().authenticated())
                // 세션 설정: STATELESS -> Oauth2 사용
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
