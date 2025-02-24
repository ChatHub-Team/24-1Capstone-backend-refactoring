package org.example.global.config.security;

import lombok.RequiredArgsConstructor;
import org.example.global.config.security.jwt.TokenAuthenticationFilter;
import org.example.global.config.security.jwt.TokenProvider;
import org.example.global.config.security.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import org.example.global.config.security.oauth.OAuth2LogoutSuccessHandler;
import org.example.global.config.security.oauth.OAuth2SuccessHandler;
import org.example.global.config.security.oauth.OAuth2UserCustomService;
import org.example.user.application.token.RefreshTokenService;
import org.example.user.repository.token.RefreshTokenRepository;
import org.example.user.application.member.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 토큰 방식으로 인증을 하기 때문에 기존에 사용하던 폼로그인, 세션 비활성화
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //헤더를 확인할 커스텀 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 토큰 재발급 URL은 인증 없이 접근 가능하도록 설정. 나머지 API URL은 인증 필요
        http.authorizeRequests()
                .requestMatchers("/","/api/token", "/api/user/**", "/api/users/**", "/api/meetings/**", "/api/health").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll();

        http.oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())//Authorization 요청과 관련된 상태 저장
                .and()
                .successHandler(oAuth2SuccessHandler()) // 인증 성공 시 실행할 핸들러
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .deleteCookies("refresh_token", "access_token")
                .clearAuthentication(true)
                .invalidateHttpSession(true);

        // /api로 시작하는 url인 경우 401 상태 코드를 반환하도록 예외 처리
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");  // 개발 중 로컬 주소
        configuration.addAllowedOrigin("https://www.coffeechat.shop"); // 프론트엔드 도메인
        configuration.addAllowedOrigin("http://www.coffeechat.shop"); // 프론트엔드 도메인
        configuration.addAllowedOrigin("https://coffeechat.shop");   // 프론트엔드 도메인
        configuration.addAllowedOrigin("http://coffeechat.shop");    // 프론트엔드 도메인
        configuration.addAllowedOrigin("https://buildmystudy.com"); // 추가된 도메인
        configuration.addAllowedOrigin("https://www.buildmystudy.com"); // 추가된 도메인
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("refresh_token");
        configuration.addExposedHeader("Set-Cookie");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Set-Cookie"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // /api/**
        return source;
    }

    @Bean
    public OAuth2LogoutSuccessHandler logoutSuccessHandler() {
        return new OAuth2LogoutSuccessHandler(refreshTokenRepository, tokenProvider);
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }
}