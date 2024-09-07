package com.yfmf.footlog.login.config;

import com.yfmf.footlog.login.handler.CustomSuccessHandler;
import com.yfmf.footlog.login.jwt.filter.JwtFilter;
import com.yfmf.footlog.login.service.CustomOAuth2UserService;
import com.yfmf.footlog.login.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

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
                // CSRF disable
                .csrf(AbstractHttpConfigurer::disable)
                // From 로그인 방식 disable
                .formLogin(formLogin ->
                        formLogin.loginPage("/login"))
                // HTTP Basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)
                // JwtFilter 추가
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
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

        // CORS 설정
        http
                .cors(cors ->
                        cors.configurationSource(new CorsConfigurationSource() {

                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                                CorsConfiguration config = new CorsConfiguration();

                                // 프론트 서버의 주소
                                config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                // 허용할 요청(GET, POST 등)
                                config.setAllowedMethods(Collections.singletonList("*"));
                                config.setAllowCredentials(true);
                                // 허용할 헤더값
                                config.setAllowedHeaders(Collections.singletonList("*"));
                                config.setMaxAge(3600L);

                                // 넘겨줄 정보에 대한 허용
                                config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                                config.setExposedHeaders(Collections.singletonList("Authorization"));

                                return config;
                            }
                        }));

        return http.build();
    }
}
