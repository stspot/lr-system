package msTask.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import msTask.data.entity.Authority;
import msTask.data.entity.Role;
import msTask.data.repositority.AuthorityRepository;
import msTask.data.repositority.RoleRepository;
import msTask.enums.AuthorityEnum;
import msTask.enums.RoleEnum;
import msTask.service.DataInitService;

@Component
@RequiredArgsConstructor
public class DataInitServiceImpl implements DataInitService {

	private final AuthorityRepository authorityRepository;
	private final RoleRepository roleRepository;

	@Override
	@PostConstruct
	public void initData() {
		if (authorityRepository.count() == 0) {
			this.initAuthorities();
		}
		if (roleRepository.count() == 0) {
			this.initRoles();
		}
	}

	private void initRoles() {
		List<Authority> authorities = authorityRepository.findAll();
		for (RoleEnum ae : RoleEnum.values()) {
			Role role = new Role(ae.name(), authorities);
			this.roleRepository.save(role);
		}
	}

	private void initAuthorities() {
		for (AuthorityEnum ae : AuthorityEnum.values()) {
			this.authorityRepository.save(new Authority(ae.name()));
		}

	}
}
