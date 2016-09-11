package pw.ewen.permission;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pw.ewen.permission.entity.User;
import pw.ewen.permission.repository.UserRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(Application.class);
//		Class.forName("com.mysql.jdbc.Driver");
	}
	
	@Bean
	public CommandLineRunner demo(UserRepository repository){
		return (args) -> {
			repository.save(new User("1", "a"));
		};
	}

}
