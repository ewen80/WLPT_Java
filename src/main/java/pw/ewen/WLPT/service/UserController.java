package pw.ewen.WLPT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.repository.UserRepository;
import pw.ewen.WLPT.repository.specifications.UserSpecificationBuilder;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	//获取所有用户
	@RequestMapping(value = "/all", method=RequestMethod.GET, produces="application/json")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	//获取用户（分页）
	@RequestMapping(method = RequestMethod.GET, produces="application/json")
	public Page<User> getUsersWithPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
									   @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
									   @RequestParam(value = "search" ) String search){
		UserSpecificationBuilder builder = new UserSpecificationBuilder();
		String operationSetExper =
		return userRepository.findAll(new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
	}

	@RequestMapping(value="/{userId}", method=RequestMethod.GET, produces="application/json")
	public User getOneUser(@PathVariable("userId") String userId){
		return userRepository.findOne(userId);
	}

	@RequestMapping(method=RequestMethod.POST, produces="application/json")
	public User save(@RequestBody User user){
	    return this.userRepository.save(user);
    }

    @RequestMapping(value = "/{userIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("userIds") String userIds){
		String[] arrUserIds = userIds.split(",");
		for(String id : arrUserIds){
			this.userRepository.delete(id);
		}

	}
}
