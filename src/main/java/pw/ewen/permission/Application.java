package pw.ewen.permission;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pw.ewen.permission.entity.Role;
import pw.ewen.permission.entity.User;
import pw.ewen.permission.repository.RoleRepository;
import pw.ewen.permission.repository.UserRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(Application.class);
//		Class.forName("com.mysql.jdbc.Driver");
	}
	
	@Bean
	public CommandLineRunner demo(UserRepository userRepo, RoleRepository roleRepo){
		return (args) -> {
			Role role1 = roleRepo.save(new Role("role1"));
			
			userRepo.save(new User("user1", role1));
			userRepo.save(new User("user2", role1));
			userRepo.save(new User("user3", role1));
		};
	}

}
