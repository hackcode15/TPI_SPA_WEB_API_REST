package com.app.JWTImplementation.config;

import java.util.Arrays;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.JWTImplementation.JWT.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // nuevo
                .authorizeHttpRequests(endpoint -> {

                    // ░░░░░░░░░░░░░░░░░░░░CONFIGURACION DE ENDPOINTS░░░░░░░░░░░░░░░░░░░░░░░░░░░

                    // ░░░░░░░░░░░░░░░░░░░░░░░░░░░ENPOINTS PUBLICOS░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                    endpoint.requestMatchers(
                            // endpoint de autenticacion (Login y Register)
                            "/auth/**",
                            // endpoints de la documentacion de swagger
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-ui.html",
                            "/api-docs",
                            // endpoint de la pagina de error por defecto
                            "/error",
                            // endpoints propios
                            "/api/availability/**",
                            "/api/service-spa/**",
                            "/api/schedule/**",
                            "/api/professional/list"
                    ).permitAll();

                    // ░░░░░░░░░░░░░░░░░░░¿DONDE VEO LAS OTRAS CONFIGURACIONES?░░░░░░░░░░░░░░░░░░
                    // Se encuentran en cada defenicion del endpoint en los controladores
                    // con la anotacion @PreAuthorize("hasRole('')") o @PreAuthorize("hasAnyRole('')")

                    // ░░░░░░░░░░░░░░░░░░░░░░░░░░ENPOINTS DE CLIENTES░░░░░░░░░░░░░░░░░░░░░░░░░░░░

                    // ░░░░░░░░░░░░░░░░░░░░░░ENPOINTS DE ADMINISTRADORES░░░░░░░░░░░░░░░░░░░░░░░░░
                    // ejemplo
                    /*endpoint.requestMatchers(
                            "/api/reserve/list-info",
                            "/api/reserve/info/{id}"
                    ).hasRole("ADMIN");*/

                    // ░░░░░░░░░░░░░░░░░░░░░░░ENPOINTS DE DESARROLLADORES░░░░░░░░░░░░░░░░░░░░░░░░░


                    // ░░░░░░░░░░░░░░░LOS DEMAS ENDPOINTS NECESITAN AUTENTICACION░░░░░░░░░░░░░░░░░
                    endpoint.anyRequest().authenticated();

                })
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exc -> // solo se activara si viene de .requestMatchers().hasRole() <- ejemplo. No por las anotaciones de @PreAuthorize
                        exc.accessDeniedHandler(((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("Acceso denegado: No tienes los permisos necesarios");
                        })))
                .build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:4200", "https://spa-sentirsebien.netlify.app/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Cache de 1 hora para OPTIONS

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

