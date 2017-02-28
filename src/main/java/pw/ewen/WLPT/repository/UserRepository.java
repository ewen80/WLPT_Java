package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
}
