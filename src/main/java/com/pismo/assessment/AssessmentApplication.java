package com.pismo.assessment;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.pismo.assessment.repository")
@OpenAPIDefinition(
	info = @Info(title = "Pismo Assessment",
			     version = "${assessment.version}",
			     description = "Documentation for the assessment ${assessment.version}")
)
public class AssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

}
