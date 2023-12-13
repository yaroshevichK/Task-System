package com.example.testtask.conf;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

import static com.example.testtask.util.DataOpenApi.BEARER;
import static com.example.testtask.util.DataOpenApi.DESCRIPTION_UI;
import static com.example.testtask.util.DataOpenApi.DEV_EMAIL;
import static com.example.testtask.util.DataOpenApi.DEV_NAME;
import static com.example.testtask.util.DataOpenApi.HEADER_AUTH;
import static com.example.testtask.util.DataOpenApi.JWT_FORMAT;
import static com.example.testtask.util.DataOpenApi.JWT_REQ;
import static com.example.testtask.util.DataOpenApi.JWT_SCHEMA;
import static com.example.testtask.util.DataOpenApi.READ;
import static com.example.testtask.util.DataOpenApi.TITLE_UI;
import static com.example.testtask.util.DataOpenApi.URL_UI;
import static com.example.testtask.util.DataOpenApi.WRITE;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI myOpenApi() {
        Contact contact = new Contact()
                .name(DEV_NAME)
                .email(DEV_EMAIL);

        Server server = new Server()
                .url(URL_UI);

        Info info = new Info()
                .contact(contact)
                .title(TITLE_UI)
                .description(DESCRIPTION_UI);

        Components securitySchemes = new Components().addSecuritySchemes(JWT_SCHEMA,
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER)
                        .bearerFormat(JWT_FORMAT)
                        .in(SecurityScheme.In.HEADER)
                        .name(HEADER_AUTH));

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(JWT_REQ, Arrays.asList(READ, WRITE));

        return new OpenAPI()
                .info(info)
                .servers(List.of(server))
                .components(securitySchemes)
                .addSecurityItem(securityRequirement);
    }
}
