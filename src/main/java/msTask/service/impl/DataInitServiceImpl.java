package msTask.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import msTask.data.entity.User;
import msTask.data.repositority.UserRepository;
import msTask.exception.UserException;
import msTask.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @PostConstruct
    public void initData() throws UserException {
        if (authorityRepository.count() == 0) {
            this.initAuthorities();
        }
        if (roleRepository.count() == 0) {
            this.initRoles();
        }
        if (this.userRepository.count() < 2) {
            //this.initUsers();
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

    private void initUsers() throws UserException {
        this.privateRegister100Users();
    }

    private void privateRegister100Users() throws UserException {
        User user = null;
        for (int i = 1; i <= 100; i++) {
            user = new User();
            user.setEmail(String.format("user%d@example.com", i));
            user.setPassword(this.bCryptPasswordEncoder.encode("5zh318A!!!$%" + i));
            user.setFirstName("FirstNameOfUser" + i);
            user.setLastName("LastNameOfUser" + i);
            user.setBirthday(generateRandomBirthDate());
            user.setPhoneNumber("+0000000000000" + i);
            this.authService.register(user);
        }
    }

    private static LocalDate generateRandomBirthDate() {
        LocalDate startDate = LocalDate.of(1975, 1, 1);
        LocalDate endDate = LocalDate.of(2010, 12, 31);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
        return startDate.plusDays(randomDays);
    }
}
