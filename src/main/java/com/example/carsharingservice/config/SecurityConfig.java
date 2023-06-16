package com.example.carsharingservice.config;

import com.example.carsharingservice.security.CustomUserDetailsService;
import com.example.carsharingservice.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                       auth.requestMatchers(HttpMethod.POST,"/register",
                                       "/login", "/swagger-ui/**",
                                       "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                               .requestMatchers(HttpMethod.GET, "/health-check",
                                       "/cars", "/swagger-ui/**",
                                       "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                               .requestMatchers(HttpMethod.POST, "/cars",
                                       "/rentals/{id}/return").hasRole("MANAGER")
                               .requestMatchers(HttpMethod.POST, "/rentals",
                                       "/payments").hasAnyRole("MANAGER", "CUSTOMER")
                               .requestMatchers(HttpMethod.GET, "/users/me",
                                       "/cars/{id}",
                                       "/payments").hasAnyRole("MANAGER", "CUSTOMER")
                               .requestMatchers(HttpMethod.GET, "/rentals", "/rentals/{id}",
                                       "/payments",
                                       "/payments/success",
                                       "/payments/cancel").hasRole("MANAGER")
                               .requestMatchers(HttpMethod.GET,"/cars").permitAll()
                               .requestMatchers(HttpMethod.PATCH, "/cars/{id}").hasRole("MANAGER")
                               .requestMatchers(HttpMethod.PUT, "users/{id}/role")
                                                .hasRole("MANAGER")
                               .requestMatchers(HttpMethod.PUT, "users/me")
                                                .hasAnyRole("MANAGER", "CUSTOMER")
                               .requestMatchers(HttpMethod.DELETE, "/cars/{id}").hasRole("MANAGER")
                               .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .httpBasic(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .build();
    }
}
