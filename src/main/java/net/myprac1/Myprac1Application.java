package net.myprac1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class Myprac1Application {

	public static void main(String[] args) {
		SpringApplication.run(Myprac1Application.class, args);
	}
	
	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)          
		  .groupName("my-prac1")
	      .select()
//	      .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
	      .paths(PathSelectors.ant("/api/**"))
	      .build()
	      .apiInfo(apiInfo());
	}
	 
	private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfo(
	      "My REST API",
	      "Some custom description of API.",
	      "API TOS",
	      "Terms of service",
	      "myeaddress@company.com",
	      "License of API",
	      "API license URL");
	    return apiInfo;
	}


	
	
	/*
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("my-prac1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("example.api.control"))
                .paths(PathSelectors.ant("/api/**"))
                .build();

    }
*/
}
