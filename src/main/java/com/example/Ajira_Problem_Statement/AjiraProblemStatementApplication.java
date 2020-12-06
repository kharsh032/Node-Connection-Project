package com.example.Ajira_Problem_Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AjiraProblemStatementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AjiraProblemStatementApplication.class, args);
	}

}
