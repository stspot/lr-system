package msTask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	//TODO...
	private final String[] allowedLinks = {"/auth/login", "/auth/register", "/auth/confirm-registration/**",
							"/auth/reset-password", "/auth/create-new-password/**", "/swagger-ui/**", "/v3/**"};

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authR -> authR
						.requestMatchers(allowedLinks).permitAll()
						.anyRequest().authenticated())
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
}
