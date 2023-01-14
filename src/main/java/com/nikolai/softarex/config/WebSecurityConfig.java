package com.nikolai.softarex.config;

import com.nikolai.softarex.filter.JwtFilter;
import com.nikolai.softarex.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private JwtFilter jwtFilter;

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(JwtFilter jwtFilter, CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http = http.csrf().disable();

        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http.authorizeHttpRequests()
                .requestMatchers("/v1/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and();

        http = http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
