package pw.ewen.permission.entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*
 * 系统用户
 * 一个用户只能属于一个角色（后期可以扩展至属于多个角色）
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 5844614718392473692L;
	
	private String ID;
	private String name;
	private Role role;
	
	protected User(){}
	
	public User (String name) {
		this.name = name;
	}
	
	public User(String name, Role role){
		this(name);
		this.role = role;
	}
	
	@Id
	@GeneratedValue(generator="UUID")
	@GenericGenerator(name="UUID", strategy="uuid")
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
