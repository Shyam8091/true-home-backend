package com.example.true_home.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SessionAuthenticationFilter sessionAuthenticationFilter;

    // Use constructor injection to ensure the filter is properly injected
    public SecurityConfig(SessionAuthenticationFilter sessionAuthenticationFilter) {
        this.sessionAuthenticationFilter = sessionAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/truehome/api/generateOtp","/truehome/api/signup").permitAll() // Allow public access to OTP generation
                .antMatchers(HttpMethod.GET, "/truehome/api/listing").permitAll()
                .antMatchers(HttpMethod.GET, "/truehome/api/listing/*").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()               // All other requests need authentication
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless sessions

        // Add the session filter directly without referencing UsernamePasswordAuthenticationFilter
        http.addFilterBefore(sessionAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

