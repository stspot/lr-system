package msTask.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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
import msTask.web.response.AuthResponseModel;

@SpringBootTest(classes = { MsTaskApplication.class })
class AuthServiceImplTest {
	
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
	void testRegister() throws UserException {
		this.authService.register(getUserUser());
		String foundUserUsername = this.userService.findByUsername(this.getUserUser().getUsername()).getUsername();
		assertEquals(this.getUserUser().getUsername(), foundUserUsername);
	}

	@Test
	void testLogin() throws UserException {
		User u = new User();
		u.setEmail("zstefchev@gmail.com");
		u.setPassword("sM6+JB)!&Z^+g.)*");
		AuthResponseModel login = this.authService.login(u);
		 assertTrue(login.getToken().length() > 9);
	}

	@Test
	void testConfirmRegistration() throws UserException {
		User newUser = this.authService.register(getUserUser());
		String activationLink = newUser.getActivationLink();
		User fUser = this.userService.findByActivationLink(activationLink);
		this.authService.confirmRegistration(activationLink);
		fUser = this.userService.findById(fUser.getId());
		boolean enabled = fUser.isEnabled();
		assertTrue(enabled);
	}
	
	@Test
	void testResetPassword() throws UserException {
		String resetPasswordLink = this.authService.resetPassword(this.adminUser.getEmail());
		String[] arr = resetPasswordLink.split("/");
		String lastElement = arr[arr.length - 1];
		assertTrue(lastElement.length() > 9);
	}

	@Test
	void testCreateNewPassword() throws UserException {
		String resetPasswordLink = this.authService.resetPassword(this.adminUser.getEmail());
		String[] arr = resetPasswordLink.split("/");
		String uniqueStringForPasswordReset = arr[arr.length - 1];
		this.authService.createNewPassword(uniqueStringForPasswordReset, "sM6+JB)!&Z^+g.)!!!");
		User u = new User();
		u.setEmail("zstefchev@gmail.com");
		u.setPassword("sM6+JB)!&Z^+g.)!!!");
		AuthResponseModel login = this.authService.login(u);
		 assertTrue(login.getToken().length() > 9);
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
	
	private User getUserUser(){
		User u = new User();
		u.setUsername("ani");
		u.setPassword("sM6+JB)!&Z^+g.)!");
		u.setEmail("zstefchev@yahoo.com");
		u.setFirstName("Ani");
		u.setLastName("Nikolaeva");
		u.setBirthday(LocalDate.parse("1988-01-18"));
		u.setPhoneNumber("+111111111111");
		return u;
	}
}
