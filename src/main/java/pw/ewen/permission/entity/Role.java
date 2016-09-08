package pw.ewen.permission.entity;
import java.util.HashSet;
import java.util.Set;

/*
 * 系统角色
 */
public class Role {

	private String ID;
	private String name;
	
	private Set<User> users;
	private Set<OperationRange> operationRanges;
	

	public Role(String iD, String name) {
		super();
		ID = iD;
		this.name = name;
		
		users = new HashSet<>();
		operationRanges = new HashSet<>();
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<OperationRange> getOperationRanges() {
		return operationRanges;
	}

	public void setOperationRanges(Set<OperationRange> operationRanges) {
		this.operationRanges = operationRanges;
	}	
}
