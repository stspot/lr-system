package msTask.data.repositority;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import msTask.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    User findByEmail(String email);

    User findByActivationLink(String aLink);

    User findByUniqueStringForPasswordReset(String uniqueStringForPasswordReset);

	Optional<User> findByUsername(String username);
}
