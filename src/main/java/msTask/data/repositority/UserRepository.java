package msTask.data.repositority;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import msTask.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    User findByEmail(String email);

    User findByActivationLink(String aLink);

    User findByUniqueStringForPasswordReset(String uniqueStringForPasswordReset);

	Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users ORDER BY last_name ASC, birthday ASC", nativeQuery = true)
    Page<User> findAllUsersSorted(Pageable pageable);

    @Query(value = "SELECT * FROM users u WHERE " +
            "(u.email LIKE %:searchTerm% OR " +
            "u.first_name LIKE %:searchTerm% OR " +
            "u.last_name LIKE %:searchTerm%)",
            countQuery = "SELECT COUNT(*) FROM users u WHERE " +
                    "(u.email LIKE %:searchTerm% OR " +
                    "u.first_name LIKE %:searchTerm% OR " +
                    "u.last_name LIKE %:searchTerm%)",
            nativeQuery = true)
    Page<User> searchUsers(@Param("searchTerm") String searchTerm, Pageable pageable);

}
