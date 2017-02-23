package pw.ewen.WLPT.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/*
 * 系统用户
 * 一个用户只能属于一个角色（后期可以扩展至属于多个角色）
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 5844614718392473692L;
	
//	private String id;
	private long id;
	private String name;
	private Role role;
	private String password;
	private String picture;
	
	protected User(){}
	
	public User (String name) {
		this.name = name;
	}
	
	public User(String name, Role role){
		this(name);
		this.role = role;
	}

	@Id
	@GeneratedValue
	public long getId(){ return id;}
	public void setId(long value){ id = value;}

//	@Id
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	
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
	@JoinColumn(name="role_ID")
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
