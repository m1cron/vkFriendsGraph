package ru.micron;

import static ru.micron.config.SwaggerConfig.BASE_PACKAGE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = BASE_PACKAGE)
@SpringBootApplication(scanBasePackages = BASE_PACKAGE)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}