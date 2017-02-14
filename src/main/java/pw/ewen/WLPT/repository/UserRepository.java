package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
