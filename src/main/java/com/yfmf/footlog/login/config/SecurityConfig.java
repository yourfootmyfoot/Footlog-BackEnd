package com.yfmf.footlog.login.config;

import com.yfmf.footlog.login.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // csrf disable
                .csrf(AbstractHttpConfigurer::disable)
                // From 로그인 방식 disable
                .formLogin(AbstractHttpConfigurer::disable)
                // HTTP Basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)
                // oauth2
                .oauth2Login(Customizer.withDefaults())
                // 경로별 인가 작업
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated())
                // 세션 설정: STATELESS -> Oauth2 사용
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
