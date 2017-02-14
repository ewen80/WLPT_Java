package pw.ewen.WLPT.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackages={"pw.ewen.WLPT"})
@SpringBootApplication(scanBasePackages="pw.ewen.WLPT")
public class ApplicationTest {

	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(ApplicationTest.class, args);
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
