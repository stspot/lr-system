package msTask.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import msTask.security.jwt.JWTAuthFilter;
import msTask.security.jwt.JwtAuthEntryPoint;
import static msTask.config.PathConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtAuthEntryPoint authEntryPoint;
	
	private final String[] allowedLinks = {
			AUTH_L + LOGIN_L,
			AUTH_L + REGISTER_L,
			AUTH_L + CONFIRM_REGISTRATION_L,
			AUTH_L + RESET_PASSWORD_L,
			AUTH_L + CREATE_NEW_PASSWORD_L,
			"/swagger-ui/**",
			"/v3/**"};
	
    public SecurityConfig(JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .exceptionHandling(exceptions -> exceptions
            .authenticationEntryPoint(authEntryPoint))
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(ahr -> ahr
        		.requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JWTAuthFilter jwtAuthenticationFilter() {
        return new JWTAuthFilter();
    }
}