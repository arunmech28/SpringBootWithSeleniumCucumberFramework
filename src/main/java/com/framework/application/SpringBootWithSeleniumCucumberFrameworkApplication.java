package com.framework.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.framework.pages","com.framework.runner","com.framework.stepdefinitions"})
@SpringBootApplication
public class SpringBootWithSeleniumCucumberFrameworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWithSeleniumCucumberFrameworkApplication.class, args);
	}

}
