package pw.ewen.permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pw.ewen.permission.entity.User;
import pw.ewen.permission.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value="getallusers", method=RequestMethod.GET, produces="application/json")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
}
