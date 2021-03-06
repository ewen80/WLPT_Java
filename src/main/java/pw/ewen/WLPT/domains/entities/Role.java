package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色
 */
@Entity
//@JsonIgnoreProperties(value={"users", "handler", "hibernateLazyInitializer"})
//@JsonIdentityInfo(
//		generator = ObjectIdGenerators.PropertyGenerator.class,
//		property = "id")
public class Role implements Serializable {

	private static final long serialVersionUID = 1888955493407366629L;
	@Id
	private String id;
	private String name;

	@JsonManagedReference(value = "user")
	@OneToMany(mappedBy="role", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	@JsonManagedReference(value = "range")
	@OneToMany(mappedBy = "role")
	private Set<ResourceRange>	resourceRanges = new HashSet<>();

	protected Role(){}
	public Role(String id, String name) {
		this.name = name;
		this.id = id;
	}


//	@GeneratedValue(generator="UUID")
//	@GenericGenerator(name="UUID", strategy="uuid")
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

	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		return id != null ? id.equals(role.id) : role.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
