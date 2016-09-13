package pw.ewen.permission.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"pw.ewen.permission"})
public class ApplicationTest {

	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(ApplicationTest.class);
	}
	
//	@Bean
//	public CommandLineRunner demo(UserRepository userRepo, RoleRepository roleRepo){
//		return (args) -> {
//			Role role1 = roleRepo.save(new Role("role1"));
//			
//			userRepo.save(new User("user1", role1));
//			userRepo.save(new User("user2", role1));
//			userRepo.save(new User("user3", role1));
//		};
//	}

}
