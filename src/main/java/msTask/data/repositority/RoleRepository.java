package msTask.data.repositority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import msTask.data.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	
    Role findByName(String name);
}