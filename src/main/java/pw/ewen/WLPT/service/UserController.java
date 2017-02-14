package pw.ewen.WLPT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pw.ewen.WLPT.entity.User;
import pw.ewen.WLPT.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	@RequestMapping(value="/{userId}", method=RequestMethod.GET, produces="application/json")
	public User getOneUser(@PathVariable("userId") String userId){
		return userRepository.findOne(userId);
	}

	@RequestMapping(method=RequestMethod.POST, produces="application/json")
	public User save(@RequestBody User user){
	    return this.userRepository.save(user);
    }
}
