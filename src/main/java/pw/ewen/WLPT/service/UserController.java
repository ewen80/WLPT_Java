package pw.ewen.WLPT.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.repository.UserRepository;
import pw.ewen.WLPT.repository.specifications.UserSpecificationBuilder;
import pw.ewen.WLPT.repository.specifications.core.SearchOperation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
									   @RequestParam(value = "filter" ) String filter){
		UserSpecificationBuilder builder = new UserSpecificationBuilder();
		String operationSetExper = StringUtils.join(SearchOperation.SIMPLE_OPERATION_SET, '|');
		Pattern pattern = Pattern.compile(
				"(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
		Matcher matcher = pattern.matcher(filter + ",");
		while (matcher.find()) {
			builder.with(
					matcher.group(1), //字段
					matcher.group(2),	//操作
					matcher.group(4),	//值
					matcher.group(3),	//前置操作符
					matcher.group(5));	//后置操作符
		}
		Specification<User> spec = builder.build();
		return userRepository.findAll(spec, new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
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
