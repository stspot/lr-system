package msTask.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import msTask.MsTaskApplication;
import msTask.data.entity.User;
import msTask.data.repositority.UserRepository;
import msTask.exception.UserException;
import msTask.service.AuthService;
import msTask.service.EmailService;
import msTask.service.UserService;

@SpringBootTest(classes = { MsTaskApplication.class })
class UserServiceImplTest {
	
	private User adminUser = null;

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@MockBean
	private EmailService emailService;

	@BeforeEach
	void setUp() throws Exception {
		adminUser = this.authService.register(getUserAdmin());
		adminUser.setEnabled(true);
		adminUser = this.userRepository.save(adminUser);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.userRepository.deleteAll();
	}

	@Test
	void testGetByEmail() {
		User userByEmail = this.userService.getByEmail("zstefchev@gmail.com");
		assertEquals("zstefchev@gmail.com", userByEmail.getEmail());
	}

	@Test
	void testGetUserDetailsByEmail() {
		User userByEmail = this.userService.getUserDetailsByEmail("zstefchev@gmail.com");
		assertEquals("zstefchev@gmail.com", userByEmail.getEmail());
	}

	@Test
	void testDeleteUserById() throws UserException {
		this.userService.deleteUserById(this.adminUser.getId());
		assertEquals(0, this.userRepository.count());
	}

	@Test
	void testFindByUniqueStringForResetPassword() throws UserException {
		String resetPasswordLink = this.authService.resetPassword("zstefchev@gmail.com");
		String[] arr = resetPasswordLink.split("/");
		String uniqueStringForPasswordReset = arr[arr.length - 1];
		User fUser = this.userService.findByUniqueStringForResetPassword(uniqueStringForPasswordReset);
		assertEquals(this.adminUser.getEmail(), fUser.getEmail());
	}

	@Test
	void testFindById() throws UserException {
		User userById = this.userService.findById(this.adminUser.getId());
		assertEquals(this.adminUser.getId(), userById.getId());
	}

	@Test
	void testFindAll() {
		List<User> allUsers = this.userService.findAll();
		assertEquals(allUsers.size(), this.userRepository.count());
	}
	
	@Test
	void updateUser() throws UserException {
		User user = new User();
		user.setId(this.adminUser.getId());
		user.setFirstName("Ani");
		user.setLastName("Nikolaeva");
		user.setPhoneNumber("+111111111111");
		User updatedUser = this.userService.updateUser(user);
		assertEquals("Ani", updatedUser.getFirstName());
		assertEquals("Nikolaeva", updatedUser.getLastName());
		assertEquals("+111111111111", updatedUser.getPhoneNumber());
	}
	
	@Test
	void deleteUser() throws UserException {
		this.userService.deleteUserById(this.adminUser.getId());
		assertEquals(0, this.userRepository.count());
	}
	
	private User getUserAdmin(){
		User u = new User();
		u.setUsername("zstefchev1");
		u.setPassword("sM6+JB)!&Z^+g.)*");
		u.setEmail("zstefchev@gmail.com");
		u.setFirstName("Zvezdomir");
		u.setLastName("Stefchev");
		u.setBirthday(LocalDate.parse("1985-01-21"));
		u.setPhoneNumber("+000000000000");
		return u;
	}
}
