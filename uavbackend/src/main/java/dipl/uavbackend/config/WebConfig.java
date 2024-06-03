package dipl.uavbackend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Дозволяє запити з будь-якого джерела
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Дозволяє вказані HTTP-методи
                .allowedHeaders("*"); // Дозволяє всі заголовки
    }


}