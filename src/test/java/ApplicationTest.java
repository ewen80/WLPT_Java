import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pw.ewen.permission.entity.User;
import pw.ewen.permission.repository.UserRepository;

@SpringBootApplication
public class ApplicationTest {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationTest.class);
	}
	
//	@Bean
//	public CommandLineRunner demo(UserRepository repository){
//		return (args) -> {
//			repository.save(new User("1", "a"));
//		};
//	}

}
