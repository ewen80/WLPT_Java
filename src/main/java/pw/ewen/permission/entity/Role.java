package pw.ewen.permission.entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	private String ID;
	private String name;
	
	
	private Set<User> users;
//	private Set<OperationRange> operationRanges;
	
	protected Role(){}
	
	public Role(String name) {
		this.name = name;
		
		users = new HashSet<>();
//		operationRanges = new HashSet<>();
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

	@OneToMany(mappedBy="role")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

//	public Set<OperationRange> getOperationRanges() {
//		return operationRanges;
//	}
//
//	public void setOperationRanges(Set<OperationRange> operationRanges) {
//		this.operationRanges = operationRanges;
//	}	
}
