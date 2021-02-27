package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

	private static final long serialVersionUID = 1888955493407366629L;
	private String id;
	private String name;
	private boolean deleted = false;	//软删除标志

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
