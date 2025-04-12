package com.app.JWTImplementation;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;

@SpringBootTest
public abstract class TestConfig {
    @BeforeAll
    public static void loadEnv() {
        // Obtiene la ruta absoluta del directorio del proyecto
        String projectDir = Paths.get("").toAbsolutePath().toString();

        Dotenv dotenv = Dotenv.configure()
                .directory(projectDir) // Usa la ruta absoluta del proyecto
                .filename(".env") // Especifica el nombre del archivo
                .load();

        dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
        );
    }
}