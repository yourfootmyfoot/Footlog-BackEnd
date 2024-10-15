package com.yfmf.footlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @EnableAspectJAutoProxy //어플리케이션 내에서 AOP를 활성화한다.
@SpringBootApplication
@EnableJpaAuditing
public class FootlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootlogApplication.class, args);
	}

}