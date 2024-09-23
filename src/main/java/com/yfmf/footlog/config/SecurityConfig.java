package com.yfmf.footlog.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.stream.Stream;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LIST = {
            "/api/auth/**",
            "/v3/api-docs/**",       // Swagger 문서
            "/swagger-ui/**",        // Swagger UI
            "/swagger-ui.html"     // Swagger UI HTML

    };



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {

        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정 추가
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(mvc.pattern("/api/clubs/**")).authenticated()  // "/api/clubs" 엔드포인트는 인증 없이도 접근 가능하게 설정
                        .requestMatchers(this.createMvcRequestMatcherForWhiteList(mvc)).permitAll()
                        .anyRequest().authenticated());
        // Spring Security Custom Filter 적용 - Form '인증'에 대해서 적용

        return httpSecurity.build();
    }

    // CORS 설정 메서드 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:3000"); // 모든 도메인 허용
        configuration.addAllowedMethod("*");        // 모든 HTTP 메서드 허용 (GET, POST 등)
        configuration.addAllowedHeader("*");        // 모든 헤더 허용
        configuration.setAllowCredentials(true);    // 쿠키 허용
        configuration.addExposedHeader("Authorization"); // 클라이언트에서 사용할 수 있는 헤더 추가

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public MvcRequestMatcher.Builder mvcRequestMatcherBuilder(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    private MvcRequestMatcher[] createMvcRequestMatcherForWhiteList(MvcRequestMatcher.Builder mvc) {
        return Stream.of(WHITE_LIST).map(mvc::pattern).toArray(MvcRequestMatcher[]::new);
    }

}