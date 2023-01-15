package com.adin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception, RuntimeException {
        http.headers().frameOptions().disable()
        .httpStrictTransportSecurity().disable()
        .and().authorizeRequests()
        .antMatchers("/").permitAll()
        .and().csrf().disable().requestCache().and().logout()
        .invalidateHttpSession(true).and().sessionManagement()
        .sessionFixation().newSession().maximumSessions(10).maxSessionsPreventsLogin(true);
    }
}
