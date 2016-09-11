package pw.ewen.permission.entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*
 * 系统用户
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 5844614718392473692L;

	@Id
	private String ID;
	
	@Column(nullable = false)
	private String name;
//	private Set<Role> roles;
	
	protected User(){}
	
	public User(String iD, String name) {
		super();
		ID = iD;
		this.name = name;
		
//		roles = new HashSet<>();
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public Set<Role> getRoles() {
//		return roles;
//	}
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}
	
}
