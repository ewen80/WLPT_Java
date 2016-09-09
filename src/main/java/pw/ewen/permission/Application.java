package pw.ewen.permission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
	
//	@Bean
//	public CommandLineRunner demo(UserRepository repository){
//		return (args) -> {
//			repository.save(new User("1", "a"));
//		};
//	}

}
