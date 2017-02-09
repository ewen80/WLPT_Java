package pw.ewen.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pw.ewen.permission.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {

}
