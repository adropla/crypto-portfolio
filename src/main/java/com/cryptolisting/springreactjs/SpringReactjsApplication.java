package com.cryptolisting.springreactjs;

import com.cryptolisting.springreactjs.service.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringReactjsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactjsApplication.class, args);
	}

}
