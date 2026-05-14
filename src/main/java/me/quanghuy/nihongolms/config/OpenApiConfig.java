package me.quanghuy.nihongolms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Cấu hình Swagger/OpenAPI với JWT Bearer Token Authorization.
 * Sau khi login, copy token và paste vào ô "Authorize" trên Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/api")
                                .description("Local Development Server")
                ))
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, createSecurityScheme()));
    }

    private Info apiInfo() {
        return new Info()
                .title("NihongoLMS API")
                .version("1.0.0")
                .description("""
                        **日本語学習管理システム** - Personal Japanese Learning Management System
                        
                        API documentation cho hệ thống quản lý học tiếng Nhật cá nhân.
                        Bao gồm: Vocabulary, Flashcard SRS, Quiz, Gamification, Real-time Quiz Room, CV Generator.
                        
                        ### Authentication
                        1. Gọi `POST /auth/register` để tạo tài khoản
                        2. Gọi `POST /auth/login` để lấy JWT token
                        3. Click nút **Authorize** 🔒 phía trên, paste token vào
                        """)
                .contact(new Contact()
                        .name("Quang Huy")
                        .email("quanghuy@nihongolms.me"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Nhập JWT token (không cần prefix 'Bearer')");
    }
}
