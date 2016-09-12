package pw.ewen.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.permission.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	
}
