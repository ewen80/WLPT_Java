package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
    List<Role> findByName(String name);
}
