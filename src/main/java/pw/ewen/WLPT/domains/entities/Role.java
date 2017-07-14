package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import pw.ewen.WLPT.domains.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
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
//		property = "roleId")
public class Role extends  Resource implements Serializable {

	private static final long serialVersionUID = 1888955493407366629L;
	private String roleId;
	private String name;

	@JsonManagedReference(value = "user")
	private Set<User> users;
	@JsonManagedReference(value = "range")
	private Set<ResourceRange>	resourceRanges;

	protected Role(){}
	public Role(String roleId, String name) {
		this.name = name;
		this.roleId = roleId;
		this.users = new HashSet<>();
		this.resourceRanges = new HashSet<>();
	}

	@Column(nullable = false, unique = true)
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
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
		sb.append("roleId='").append(roleId).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		return roleId != null ? roleId.equals(role.roleId) : role.roleId == null;
	}

	@Override
	public int hashCode() {
		return roleId != null ? roleId.hashCode() : 0;
	}
}
