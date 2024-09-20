package msTask.data.repositority;

import java.time.LocalDate;
import java.util.List;
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

    @Query(value = "SELECT * FROM users u ORDER BY " +
            "CASE WHEN :isAscPrimary = true THEN u.:primarySortField END ASC, " +
            "CASE WHEN :isAscPrimary = false THEN u.:primarySortField END DESC, " +
            "CASE WHEN :isAscSecondary = true THEN u.:secondarySortField END ASC, " +
            "CASE WHEN :isAscSecondary = false THEN u.:secondarySortField END DESC",
            nativeQuery = true)
    Page<User> findAllSorted(Pageable pageable,
                             @Param("primarySortField") String primarySortField,
                             @Param("secondarySortField") String secondarySortField,
                             @Param("isAscPrimary") boolean isAscPrimary,
                             @Param("isAscSecondary") boolean isAscSecondary);

    @Query(value = "SELECT * FROM users u WHERE " +
            "(u.email LIKE %:searchTerm% OR " +
            "u.first_name LIKE %:searchTerm% OR " +
            "u.last_name LIKE %:searchTerm% OR " +
            "u.birthday = :birthday)",
            countQuery = "SELECT COUNT(*) FROM users u WHERE " +
                    "(u.email LIKE %:searchTerm% OR " +
                    "u.first_name LIKE %:searchTerm% OR " +
                    "u.last_name LIKE %:searchTerm% OR " +
                    "u.birthday = :birthday)",
            nativeQuery = true)
    Page<User> searchUsers(@Param("searchTerm") String searchTerm,
                           @Param("birthday") LocalDate birthday,
                           Pageable pageable);
}
