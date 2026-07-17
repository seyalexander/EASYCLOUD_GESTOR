package com.SeyaCloudGestion.GestionSistema.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

        @Value("${encriptacion.bcrypt.strength:10}")
        private int bcryptStrength;

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final JwtInterceptor jwtInterceptor;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtInterceptor jwtInterceptor) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                this.jwtInterceptor = jwtInterceptor;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(bcryptStrength);
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                /*
                 * configuration.setAllowedOrigins(Arrays.asList(
                 * "http://localhost",
                 * "http://localhost:4200"));
                 */

                configuration.setAllowedOrigins(Arrays.asList(
                                "http://localhost",
                                "http://127.0.0.1:5500",
                                "http://localhost:5500",
                                "http://localhost:4200"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers(
                                                                "/api/v1/auth/**",
                                                                "/api/v1/ws/**",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**",
                                                                "/swagger-ui.html",
                                                                "/h2-console/**")
                                                .permitAll()

                                                // .requestMatchers("/api/v1/**").authenticated()
                                                .requestMatchers("/api/v1/**").permitAll()
                                                .anyRequest().permitAll())
                                // .addFilterBefore(jwtAuthenticationFilter,
                                // UsernamePasswordAuthenticationFilter.class)
                                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

                return http.build();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
                /*
                 * registry.addInterceptor(jwtInterceptor)
                 * .addPathPatterns("/api/v1/**")
                 * .excludePathPatterns(
                 * "/api/v1/auth/**",
                 * "/api/v1/ws/**",
                 * "/swagger-ui/**",
                 * "/v3/api-docs/**",
                 * "/swagger-ui.html");
                 */
        }

}
