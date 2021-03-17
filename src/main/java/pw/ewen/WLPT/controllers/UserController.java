package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.UserService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public UserController(UserService userService, RoleService roleService){
		this.roleService = roleService;
		this.userService = userService;
	}

	//将user对象转为DTO对象的内部辅助类
	static class UserDTOConverter implements Converter<User, UserDTO>{
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

		if(filter.isEmpty()){
			userResults =  this.userService.findAll(new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
		}else{
			SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
			userResults =  this.userService.findAll(builder.build(filter), new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
		}
		return userResults.map(new UserDTOConverter());
	}

	@RequestMapping(value="/{userId}", method=RequestMethod.GET, produces="application/json")
	public UserDTO getOne(@PathVariable("userId") String userId){
		User user = this.userService.findOne(userId);
		return user == null ? null : UserDTO.convertFromUser(user);
	}

	@RequestMapping(method=RequestMethod.POST, produces="application/json")
	public UserDTO save(@RequestBody UserDTO dto){
		User user = dto.convertToUser(this.roleService);
		return UserDTO.convertFromUser(this.userService.save(user));
    }

    @RequestMapping(value = "/{userIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("userIds") String userIds){
		List<String> ids = Arrays.asList(userIds.split(","));
		this.userService.delete(ids);
	}
}
