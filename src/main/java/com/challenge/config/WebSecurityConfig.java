package com.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.challenge.security.JWTAuthorizationFilter;
import com.challenge.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
    	
    	JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
    	jwtAuthenticationFilter.setAuthenticationManager(authManager);
    	jwtAuthenticationFilter.setFilterProcessesUrl("/login");
    	
        return http
        	.csrf().disable()
        	.headers().frameOptions().disable()
        	.and()
            .authorizeRequests()
            .antMatchers("/h2-console").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(jwtAuthenticationFilter)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    
    @Bean
    AuthenticationManager authManager (HttpSecurity http) throws Exception {
    	return http.getSharedObject(AuthenticationManagerBuilder.class)
    			.userDetailsService(userDetailsService)
    			.passwordEncoder(passwordEncoder())
    			.and()
    			.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
