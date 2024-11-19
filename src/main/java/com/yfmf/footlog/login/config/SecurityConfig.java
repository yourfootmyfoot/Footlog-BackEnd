package com.yfmf.footlog.login.config;

import com.yfmf.footlog.login.filter.CustomLogoutFilter;
import com.yfmf.footlog.login.handler.CustomSuccessHandler;
import com.yfmf.footlog.login.jwt.filter.JwtFilter;
import com.yfmf.footlog.login.repository.RefreshRepository;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF disable
                .csrf(AbstractHttpConfigurer::disable)
                // HTTP Basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)
                // Form 로그인 방식 disable
                .formLogin(AbstractHttpConfigurer::disable)
                // JwtFilter 추가
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class)
                .addFilterAfter(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // oauth2
                .oauth2Login(auth ->
                        auth.userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(customOAuth2UserService))
                                .successHandler(customSuccessHandler)
                                .loginPage("/login")
                                .failureUrl("/login?error=true"))
                // 경로별 인가 작업
                .authorizeHttpRequests(auth ->
                        auth
                                // 로그인 경로 허용
                                .requestMatchers("/login", "/oauth2/**").permitAll()
                                // 토큰 재발급 경로 허용
                                .requestMatchers("/reissue").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.*").permitAll()
                                .anyRequest().authenticated())
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
                                config.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));

                                return config;
                            }
                        }));

        return http.build();
    }
}
