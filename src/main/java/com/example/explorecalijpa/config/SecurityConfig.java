package com.example.explorecalijpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Optional: enables @PreAuthorize, @RolesAllowed, etc.
public class SecurityConfig {

  /**
   * Defines the security filter chain for HTTP requests.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  /**
   * Provides in-memory user accounts for demonstration.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails admin = User.withUsername("admin")
        .password("{noop}password")
        .roles("ADMIN")
        .build();

    UserDetails student = User.withUsername("student")
        .password("{noop}password")
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(admin, student);
  }
}
