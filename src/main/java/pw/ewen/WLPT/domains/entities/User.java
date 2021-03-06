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
	private String name;	//用户姓名
	@JsonBackReference(value = "user")
	@ManyToOne
	@JoinColumn(name="role_Id", nullable = false)
	private Role role;	// 用户角色
	private String password;
	private String avatar;
	private boolean deleted = false;	//软删除标志

	protected  User(){}
	public User(String id, String name, String password, Role role){
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
	}


	public String getId() {
		return id;
	}
	private void setId(String id) {
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
