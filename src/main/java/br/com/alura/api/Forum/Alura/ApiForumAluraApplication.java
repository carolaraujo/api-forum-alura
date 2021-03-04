package br.com.alura.api.Forum.Alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class ApiForumAluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiForumAluraApplication.class, args);
	}

}
