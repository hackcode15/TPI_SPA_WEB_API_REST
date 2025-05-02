package com.app.JWTImplementation.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "API SPA WEB",
                description = "Esta API permite gestionar los usuarios, servicios, horarios y reservaciones de un Spa",
                //termsOfService = "www.google.com.ar",
                version = "1.0.0",
                contact = @Contact(
                        name = "Repositorio - GitHub",
                        url = "https://github.com/hackcode15/TPI_SPA_WEB_API_REST"
                        //email = "taysonm895@gmail.com"
                )
        ),
        servers = {
                @Server(
                        description = "SERVIDOR DE DESARROLLO",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "SERVIDOR DE PRODUCCION",
                        url = "https://my-api-spring-azure-c2gzdydbbah9gzeh.brazilsouth-01.azurewebsites.net"
                )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)
@SecurityScheme(
        name = "Security Token",
        description = "Access Token For My API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig { }
