package com.goncharenko.currencyexchangerate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Properties;

@Configuration
public class ApplicationConfig {

    @Bean("nameFromRoot")
    public String getProjectName() {
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("settings.gradle"))) {
            properties.load(in);
        } catch (IOException e) {
            System.out.println("File cannot be read");
            throw new RuntimeException();
        }
        String nameProject = properties.getProperty("rootProject.name");
        return nameProject;
    }

    @Bean("nameFromString")
    public String getProjectNameFromString() {
        return "currencyexchangerate";
    }

    @Bean("currentTime")
    public Instant getTime() {
        return Instant.now();
    }
}
