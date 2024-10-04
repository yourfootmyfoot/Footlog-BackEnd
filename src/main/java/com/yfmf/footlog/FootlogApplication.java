package com.yfmf.footlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// @EnableAspectJAutoProxy //어플리케이션 내에서 AOP를 활성화한다.
@SpringBootApplication
public class FootlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootlogApplication.class, args);
	}

}
