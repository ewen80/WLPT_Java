package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.UserSpecificationBuilder;
import pw.ewen.WLPT.services.UserService;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;

//	//将user对象转为DTO对象的内部辅助类
//	class userDTOConverter implements Converter<User, UserDTO>{
//		@Override
//		public UserDTO convert(User source) {
//			return  UserDTO.convertFromUser(source);
//		}
//	}
//	//获取用户（分页,查询）
//	@RequestMapping(method = RequestMethod.GET, produces="application/json")
//	public Page<UserDTO> getUsersWithPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
//										  @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
//										  @RequestParam(value = "filter", defaultValue = "") String filter){
//		Page<User> userResults;
//		Page<UserDTO> dtoResult;
//
//		if(filter.isEmpty()){
//			userResults =  this.userService.findAll(new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
//		}else{
//			UserSpecificationBuilder builder = new UserSpecificationBuilder();
//			userResults =  this.userService.findAll(builder.build(filter), new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
//		}
//		return userResults.map(new userDTOConverter());
//	}
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@PostFilter("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(filterObject.convertToUser(@roleRepository), 'read')")
	public Collection<UserDTO> getUsers(@RequestParam(value="filter",defaultValue = "") String filter){
		Collection<User> users = this.userService.findAll(filter);
		Collection<UserDTO> dtos = users.stream()
										.map( UserDTO::convertFromUser)
										.collect(toList());
		return dtos;
	}

	@RequestMapping(value="/{userId}", method=RequestMethod.GET, produces="application/json")
	@PostAuthorize("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(returnObject.convertToUser(@roleRepository), 'read')")
	public UserDTO findOne(@PathVariable("userId") String userId){
		User user = this.userService.findOne(userId);
		return user == null ? null : UserDTO.convertFromUser(user);
	}

	@RequestMapping(method=RequestMethod.POST, produces="application/json")
	@PreAuthorize("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(#dto.convertToUser(@roleRepository), 'write')")
	public UserDTO save(@RequestBody UserDTO dto){
		User user = dto.convertToUser(this.roleRepository);
		return UserDTO.convertFromUser(this.userService.save(user));
    }

    @RequestMapping(method=RequestMethod.DELETE, produces = "application/json")
	@PreFilter("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(@userService.findOne(filterObject ), 'write')")
    public void delete(@RequestBody Collection<Long> ids){
		this.userService.delete(ids);
	}
}
