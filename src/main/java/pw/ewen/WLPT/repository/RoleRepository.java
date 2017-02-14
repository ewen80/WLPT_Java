package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
