package com.experiment.departamentopessoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DepartamentoPessoalApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartamentoPessoalApplication.class, args);
    }

}
