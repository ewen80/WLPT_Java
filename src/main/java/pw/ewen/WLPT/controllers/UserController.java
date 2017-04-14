package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.specifications.UserSpecificationBuilder;
import pw.ewen.WLPT.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	@Autowired
	public UserController(UserService userService){
	this.userService = userService;
	}

	class userDTOConverter implements Converter<User, UserDTO>{
		@Override
		public UserDTO convert(User source) {
			return  UserDTO.convertFromUser(source);
		}
	}
	//获取用户（分页,查询）
	@RequestMapping(method = RequestMethod.GET, produces="application/json")
	public Page<UserDTO> getUsersWithPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
										  @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
										  @RequestParam(value = "filter", defaultValue = "") String filter){
		Page<User> userResults;
		Page<UserDTO> dtoResult;

		if(filter.isEmpty()){
			userResults =  this.userService.findAll(new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
		}else{
			UserSpecificationBuilder builder = new UserSpecificationBuilder();
			userResults =  this.userService.findAll(builder.build(filter), new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
		}
		return userResults.map(new userDTOConverter());
	}

	@RequestMapping(value="/{userId}", method=RequestMethod.GET, produces="application/json")
	public UserDTO getOne(@PathVariable("userId") String userId){
		return UserDTO.convertFromUser(this.userService.findOne(userId));
	}

	@RequestMapping(method=RequestMethod.POST, produces="application/json")
	public UserDTO save(@RequestBody User user){
		return UserDTO.convertFromUser(this.userService.save(user));
    }

    @RequestMapping(value = "/{userIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("userIds") String userIds){
		this.userService.delete(userIds);
	}
}
