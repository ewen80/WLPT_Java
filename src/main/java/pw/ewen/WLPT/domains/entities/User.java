package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pw.ewen.WLPT.annotations.ResourceType.*;
import pw.ewen.WLPT.domains.Resource;

import javax.persistence.*;
import java.io.Serializable;

/*
 * 系统用户
 * 一个用户只能属于一个角色（后期可以扩展至属于多个角色）
 */
@Entity
@pw.ewen.WLPT.annotations.ResourceType.ResourceType
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,
//		property = "id")
public class User extends Resource implements Serializable {
	private static final long serialVersionUID = 5844614718392473692L;
	
	private String id;
	private String name;
	@JsonBackReference(value = "user")
	private Role role;
	private String password;
	private String picture;
	
	protected User(){}
	
	public User(String id, String name, String password, Role role){
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	@Column(nullable = false,unique = true)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public String getPassword(){ return password;}
	public void setPassword(String password){ this.password = password;}

	@Column(nullable = true)
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	@ManyToOne
	@JoinColumn(name="role_resourceId")
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof  User){
			if(this.id.equals(((User) obj).getId())){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
