package kusitms.backend.user.infra.config;

import kusitms.backend.user.infra.handler.OAuth2LoginFailureHandler;
import kusitms.backend.user.infra.handler.OAuth2LoginSuccessHandler;
import kusitms.backend.user.infra.jwt.JWTFilter;
import kusitms.backend.user.infra.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .addFilterBefore(new JWTFilter(jwtUtil), ExceptionTranslationFilter.class)
                .oauth2Login((oauth2) -> oauth2
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/", "/login", "/login/google", "/login/naver", "/login/kakao",
                                "/onboarding", "/base",
                                "/api/v1/health-check", "/api/v1/test-error",
                                "/api/v1/test/docs","/swagger-ui/**", "/v3/api-docs/**",
                                "/api/v1/user/**",
                                "/api/v1/results/**", "/api/v1/stadium/**",
                                "/api/v1/foods", "/api/v1/entertainments",
                                "/api/v1/chatbot/**").permitAll()  // 인증이 필요 없는 경로 설정
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "https://git.hitzone.store",
                "https://frontenddev-t5wq.vercel.app",
                "https://hitzone.site",
                "https://www.hitzone.site"
        ));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);
        configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
