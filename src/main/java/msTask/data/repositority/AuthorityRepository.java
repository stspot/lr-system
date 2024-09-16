package msTask.data.repositority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import msTask.data.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
    Authority findByName(String name);
}
