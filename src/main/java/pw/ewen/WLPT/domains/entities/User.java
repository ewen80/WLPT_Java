package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统用户
 * 一个用户只能且必须属于一个角色（后期可以扩展至属于多个角色）
 */
@Entity
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,
//		property = "id")
public class User implements Serializable {
	private static final long serialVersionUID = 5844614718392473692L;

	@Id
	private String id;	// 用户ID

	@Column(nullable = false)
	private String name;	//用户姓名

//	@JsonBackReference(value = "user")
	@ManyToOne
	@JoinColumn(name="role_Id", nullable = false)
	private Role role;	// 用户角色

	@Column(nullable = false)
	private String passwordMD5 = "";

	@Column(nullable = true)
	private String avatar;

	private boolean deleted = false;	//软删除标志

	public boolean isDeleted() {
		return deleted;
	}

	protected  User(){}
	public User(String id, String name, Role role){
		this.id = id;
		this.name = name;
		this.role = role;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordMD5(){ return passwordMD5;}
	public void setPasswordMD5(String passwordMD5){ this.passwordMD5 = passwordMD5;}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
 		this.role = role;
	}

	@Override
	// 用户id相同则认为两个user相同
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
