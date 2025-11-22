package com.example.ratingsystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // REST API / Postman სტილი – CSRF არ გვჭირდება
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        // რეგისტრაცია + ლოგინი ყველასთვის ხელმისაწვდომია
                        .requestMatchers("/auth/**").permitAll()

                        // კომენტარების ნახვა ყველას შეუძლია
                        .requestMatchers(HttpMethod.GET, "/users/*/comments").permitAll()

                        // სელერის სტატისტიკა და TOP sellers – public (ან სურვილის მიხედვით)
                        .requestMatchers(HttpMethod.GET, "/seller/**").permitAll()

                        // admin-ის endpoint-ები – მხოლოდ ADMIN როლისთვის
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // სხვა ყველაფერი – ავტორიზაციას ითხოვს
                        .anyRequest().authenticated()
                )

                // მარტივი HTTP Basic ავტორიზაცია (JWT არ გვჭირდება ამ ამოცანაში)
                .httpBasic(Customizer.withDefaults());

        // ავუთენტიკაციის პროვაიდერი
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // ჩვენი CustomUserDetailsService
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
