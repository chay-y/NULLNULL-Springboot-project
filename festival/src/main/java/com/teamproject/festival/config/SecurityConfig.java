package com.teamproject.festival.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Spring Security 필터의 체인을 구성하는 역할을 담당
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인증되지 않은 모든 페이지의 요청을 허락한다는 의미
        // httpSecurity : 객체를 받아서 처리
        // authorizeHttpRequests : 요청에 대한 인가를 설정
        // requestMatchers : 특정한 요청을 매칭하기 위해서 사용
        // AntPathRequestMatcher("/**") : 경로 지정 (모든 경로에 대해)
        // permitAll() : 모든 요청을 허용한다

        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/", "/users/**").permitAll()

                // admin으로 시작하는 경로는 해당 계정이 admin일때만 접근가능하도록 설정
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // 나머지 모든 경로는 인증을 요구
                .anyRequest().authenticated())
                //.csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
                .csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
                // 로그인 경로 등록
                .formLogin((formLogin) -> formLogin
                        .loginPage("/users/login") // 로그인 페이지 url
                        .defaultSuccessUrl("/", true) //로그인 성공시 이동할 url
                        .usernameParameter("userId") // 로그인할 때 사용할 파라미터의 이름으로 id로 지정
                        .failureUrl("/users/login/error") ) // 로그인 실패시 이동할 url
                // 로그아웃 경로 등록
                .logout((logout) -> logout  // 로그인과 관련된 옵션 설정을 종료하고 logout을 설정
                        .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout")) // 로그아웃 url
                        .logoutSuccessUrl("/")      // 로그아웃에 성공하면 url설정
                        .invalidateHttpSession(true))   // 세션의 사용을 정지
                // 인증되지 않은 사용자가 접근할 때 수행되는 핸들러 등록
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))

        ;

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
