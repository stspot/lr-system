//package msTask.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Allows all endpoints
//                        .allowedOrigins("http://localhost:4200") // Allows Angular frontend
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Allows these HTTP methods
//                        .allowedHeaders("*") // Allows all headers
//                        .allowCredentials(true); // Allows cookies or authentication headers
//            }
//        };
//    }
//}
