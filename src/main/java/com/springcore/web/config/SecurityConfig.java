package com.springcore.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if ("local".equals(activeProfile)) {
            // 로컬 환경이면 모든 URL 보안 해제
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        } else {
            // 서버 환경에서는 보안 적용
            http
                    .authorizeHttpRequests(auth -> auth
                            .antMatchers("/", "/index", "/main").permitAll()
                            .antMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/webjars/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(login -> login
                            .loginPage("/login")
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutSuccessUrl("/")
                            .permitAll()
                    );
        }

        return http.build();
    }
}
