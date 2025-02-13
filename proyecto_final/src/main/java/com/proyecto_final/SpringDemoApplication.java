package com.proyecto_final;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(SpringDemoApplication.class);

    public static void main(String[] args) {
        logger.info("Iniciando la aplicación Spring Boot");
        SpringApplication.run(SpringDemoApplication.class, args);
        logger.info("Aplicación Spring Boot iniciada");
    }
}