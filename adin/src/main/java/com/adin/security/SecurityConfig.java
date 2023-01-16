package com.adin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.session.SessionRegistryImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SessionRegistryImpl SessionRegistryImpl() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception, RuntimeException {
        http.headers().frameOptions().disable()
                .httpStrictTransportSecurity().disable()
                .and().authorizeRequests()
                .antMatchers("/").permitAll()
                .and().csrf().disable().requestCache().and().logout()
                .invalidateHttpSession(true).and().sessionManagement()
                .sessionFixation().newSession().maximumSessions(10).maxSessionsPreventsLogin(true)
                .sessionRegistry(SessionRegistryImpl());;

        return http.build();
    }
}
