package com.yfmf.footlog.login.config;

import com.yfmf.footlog.login.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OAuth2UserService oAuth2UserService) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) //CSRF 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 미사용
                .authorizeHttpRequests(config ->
                        config
                                //정적 리소스 접근 허용
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.*").permitAll()

                                //로그인 관련 페이지
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/oauth2/authorization/**").permitAll()
                                .anyRequest().permitAll()

                )
                .oauth2Login(oauth2Config ->
                        oauth2Config.loginPage("/login")
                                //OAuth2 로그인 시작
                                .authorizationEndpoint(authorization ->
                                        authorization.baseUri("/oauth2/authorization"))

                                //사용자 정보 처리 서비스
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(oAuth2UserService))

                                //성공 핸들러
                                .successHandler(successHandler())
                );


        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String id = defaultOAuth2User.getAttributes().get("id").toString();
            Object properties = defaultOAuth2User.getAttributes().get("properties");
            String string = properties.toString();
            String body = """
                    {"id":"%s"}
                    {"string":"%s"}
                    """.formatted(id, string);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            PrintWriter writer = response.getWriter();
            writer.println(body);
            writer.flush();
        });
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
