package com.braingrow.security;
import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; import org.springframework.web.cors.*; import java.util.*;
@Configuration public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(12);}
 @Bean SecurityFilterChain filterChain(HttpSecurity http,JwtAuthenticationFilter jwt,@Value("${app.frontend-url:http://localhost:5173}") String frontend)throws Exception{
  return http.csrf(c->c.disable()).cors(c->c.configurationSource(corsConfiguration(frontend))).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/","/api/health","/api/auth/**","/error","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/webjars/**","/actuator/health").permitAll()
   .requestMatchers("/api/admin/**").hasRole("ADMIN").requestMatchers("/api/exercises/**").authenticated().requestMatchers("/api/learning/**").authenticated().anyRequest().authenticated())
   .addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class).build();
 }
 private CorsConfigurationSource corsConfiguration(String frontend){return req->{CorsConfiguration c=new CorsConfiguration();c.setAllowedOriginPatterns(Arrays.stream(frontend.split(",")).map(String::trim).filter(s->!s.isBlank()).toList());c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));c.setAllowedHeaders(List.of("Authorization","Content-Type","Accept","Origin"));c.setExposedHeaders(List.of("Authorization"));c.setAllowCredentials(true);return c;};}
}
