package pl.ziemniakoss.studentsresourcesmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class StudentsResourcesManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudentsResourcesManagerApplication.class, args);
	}

}
