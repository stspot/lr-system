package msTask.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class BaseBeanConfig {
	
	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
