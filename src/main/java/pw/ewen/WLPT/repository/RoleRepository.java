package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
//    List<Role> findByName(String name);
}

