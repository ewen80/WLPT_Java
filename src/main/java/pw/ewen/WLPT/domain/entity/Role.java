package pw.ewen.WLPT.domain.entity;

import com.fasterxml.jackson.annotation.*;

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
//@JsonIgnoreProperties(value={"users", "handler", "hibernateLazyInitializer"})
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,
//		property = "id")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1888955493407366629L;
	private String id;
	private String name;

	@JsonManagedReference(value = "user")
	private Set<User> users;
	@JsonManagedReference(value = "range")
	private Set<ResourceRange>	resourceRanges;

	protected Role(){}
	public Role(String id, String name) {
		this.name = name;
		this.id = id;
		this.users = new HashSet<>();
		this.resourceRanges = new HashSet<>();
	}

	@Id
//	@GeneratedValue(generator="UUID")
//	@GenericGenerator(name="UUID", strategy="uuid")
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

	@OneToMany(mappedBy="role")
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(mappedBy = "role")
	public Set<ResourceRange> getResourceRanges() { return this.resourceRanges;}
	public void setResourceRanges(Set<ResourceRange> resourceRanges) { this.resourceRanges = resourceRanges;}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Role{");
		sb.append("id='").append(id).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
