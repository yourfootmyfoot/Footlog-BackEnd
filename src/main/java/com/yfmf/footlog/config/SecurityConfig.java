//package com.yfmf.footlog.config;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
//import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
//
//import java.util.stream.Stream;
//
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private static final String[] WHITE_LIST = {
//            "/api/auth/**",
//            "/v3/api-docs/**",       // Swagger 문서
//            "/swagger-ui/**",        // Swagger UI
//            "/swagger-ui.html"     // Swagger UI HTML
//
//    };
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public MvcRequestMatcher.Builder mvcRequestMatcherBuilder(HandlerMappingIntrospector introspector) {
//        return new MvcRequestMatcher.Builder(introspector);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
//
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement((sessionManagement) ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests((request) -> request
//                        .requestMatchers(this.createMvcRequestMatcherForWhiteList(mvc)).permitAll()
//                        .anyRequest().authenticated());
//        // Spring Security Custom Filter 적용 - Form '인증'에 대해서 적용
//
//        return httpSecurity.build();
//    }
//
//    private MvcRequestMatcher[] createMvcRequestMatcherForWhiteList(MvcRequestMatcher.Builder mvc) {
//        return Stream.of(WHITE_LIST).map(mvc::pattern).toArray(MvcRequestMatcher[]::new);
//    }
//
//}