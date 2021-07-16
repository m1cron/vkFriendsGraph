package ru.micron.config;

import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.util.Collections;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@NoArgsConstructor
public class SwaggerConfig {

  public static final String BASE_PACKAGE = "ru.micron";

  @Value("${spring.application.name}")
  private String appName;

  @Bean
  @ConditionalOnMissingBean(Docket.class)
  public Docket swagger() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(appName, EMPTY, "1.0",
        EMPTY, ApiInfo.DEFAULT_CONTACT, EMPTY, EMPTY, Collections.emptyList());
  }
}
