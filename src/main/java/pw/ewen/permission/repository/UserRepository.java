package pw.ewen.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import pw.ewen.permission.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
