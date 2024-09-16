package msTask.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.RequiredArgsConstructor;
import msTask.data.entity.User;
import msTask.data.repositority.UserRepository;
import msTask.exception.UserException;

@RequiredArgsConstructor
@Configuration
public class BaseBeanConfig {

	private final UserRepository userRepository;
	
	@Bean(name = "BCryptPasswordEncoder")
	BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return username -> {
			try {
				System.out.println("userDetailsService() => " + username );
				User user = this.userRepository.findByEmail(username);
				if(user == null) throw new UserException("User not found");
				return user;
			} catch (UserException e) {
				throw new RuntimeException(e);
			}
		};
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(getBCryptPasswordEncoder());
		return authProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
