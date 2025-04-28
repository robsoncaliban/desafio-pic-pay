package com.robson.desafiopicpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtConfig jwtConfig;

    public SecurityConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        String[] roles = {"USUARIO_LOGISTA", "USUARIO_COMUM", "ADMIN"};
        http.authorizeHttpRequests(configurer -> 
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").hasRole(roles[2])
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/transacoes/enviadas/**").hasRole(roles[1])
                        .requestMatchers(HttpMethod.GET, "/api/transacoes/recebidas**").hasAnyRole(roles[0], roles[1])
                        .requestMatchers(HttpMethod.POST, "/api/transacoes").hasRole(roles[1])
                        .anyRequest().authenticated()
        );
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtConfig, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
