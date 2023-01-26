package com.nikolai.softarex.config;

import com.nikolai.softarex.filter.JwtFilter;
import com.nikolai.softarex.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity(debug = true)
@Configuration
public class WebSecurityConfig {

    private JwtFilter jwtFilter;

    private CustomUserDetailsService userDetailsService;

    private AuthenticationEntryPoint failedAuthenticationPoint;


    @Autowired
    public WebSecurityConfig(JwtFilter jwtFilter,
                             CustomUserDetailsService userDetailsService,
                             @Qualifier("failedAuthHandler") AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
        this.failedAuthenticationPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http = http.csrf().disable().cors().and();

        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http.authorizeHttpRequests()
                .requestMatchers("/v1/api/auth/**").permitAll()
                .requestMatchers("/v1/api/questionnaire/**").permitAll()
                .anyRequest().authenticated()
                .and();

        http = http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http = http.exceptionHandling().defaultAuthenticationEntryPointFor(
                failedAuthenticationPoint,
                new AntPathRequestMatcher("/v1/api/auth/validate")
        ).and();

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


}
