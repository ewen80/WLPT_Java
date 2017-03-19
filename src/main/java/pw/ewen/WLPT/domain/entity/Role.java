package pw.ewen.WLPT.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*
 * 系统角色
 */
@Entity
@JsonIgnoreProperties(value={"users", "handler", "hibernateLazyInitializer"})
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1888955493407366629L;
	private String id;
	private String name;
	
	
	private Set<User> users;
//	private Set<OperationRange> operationRanges;
	
	protected Role(){}
	
	public Role(String id, String name) {
		this.name = name;
		this.id = id;
		users = new HashSet<>();
//		operationRanges = new HashSet<>();
	}

	@Id
//	@GeneratedValue(generator="UUID")
//	@GenericGenerator(name="UUID", strategy="uuid")
	public String getID() {
		return id;
	}
	
	public void setID(String iD) {
		id = iD;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy="role")
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Role{");
		sb.append("id='").append(id).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
