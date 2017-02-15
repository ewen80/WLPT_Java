package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {
    List<Role> findByName(String name);
}
