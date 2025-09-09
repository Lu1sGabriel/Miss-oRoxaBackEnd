package org.missao.roxa.missaoroxabackend.core.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Map<String, Object> linkedin = Map.of(
                "x-linkedin", "https://www.linkedin.com/in/luisgabrielsantana/"
        );

        return new OpenAPI()
                .info(new Info()
                        .title("Açaí Shop API with Gamification System")
                        .version("1.0.0")
                        .description("""
                                This API provides complete management for an Açaí shop, including:
                                
                                • Managing states, municipalities, and addresses
                                • Creating and viewing orders
                                • Tracking order statuses
                                • Gamification features such as user levels, experience points (XP), and rewards
                                
                                Fully documented with OpenAPI/Swagger to allow easy exploration, testing, and integration.
                                """)
                        .contact(new Contact()
                                .name("Luis Goés")
                                .email("03luis.goes@gmail.com")
                                .url("https://github.com/Lu1sGabriel")
                                .extensions(linkedin))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT"))
                );
    }

}