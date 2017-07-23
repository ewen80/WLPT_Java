package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pw.ewen.WLPT.domains.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/*
 * 系统用户
 * 一个用户只能属于一个角色（后期可以扩展至属于多个角色）
 */
@Entity
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,
//		property = "userId")
public class User extends Resource implements Serializable {
	private static final long serialVersionUID = 5844614718392473692L;
	
	private String userId;
	private String name;
	@JsonBackReference(value = "user")
	private Role role;
	private String password;
	private String picture;
	private boolean isDeleted = false;
	
	protected User(){}
	
	public User(long id, String userId, String name, String password, Role role){
		this(userId, name, password, role);
		this.setId(id);
	}

	public User(String userId, String name, String password, Role role){
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	@Column(nullable = false,unique = true)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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

	//软删除标识
	@Column(nullable = true)
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
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
			if(this.userId.equals(((User) obj).getUserId())){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
