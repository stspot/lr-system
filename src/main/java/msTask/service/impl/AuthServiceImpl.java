package msTask.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;
import msTask.service.EmailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import msTask.data.entity.Role;
import msTask.data.entity.User;
import msTask.data.repositority.RoleRepository;
import msTask.data.repositority.UserRepository;
import msTask.exception.UserException;
import msTask.models.EmailRequestModel;
import msTask.service.AuthService;
import msTask.service.JwtService;
import msTask.service.UserService;
import msTask.web.response.AuthResponseModel;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final UserService userService;
	private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
	private final EmailService emailService;

	@Override
    public AuthResponseModel login(User user) throws UserException {
        User foundByEmail = this.userService.getByEmail(user.getEmail());
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    	String jwtToken = jwtService.generateToken(foundByEmail);
    	return new AuthResponseModel(jwtToken);
    }

	@Override
    public User register(User user) {
		Role role;
    	if(userRepository.count() == 0) {
    		role = roleRepository.findByName("ROLE_ADMIN");
    	} else {
    		role = roleRepository.findByName("ROLE_USER");
    	}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(role);
		user.setEnabled(false);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		String aLink = UUID.randomUUID().toString();
		user.setActivationLink(aLink);
		user.setMaximumActivationLinkTime(LocalDateTime.now().plusHours(2));

		String email = user.getEmail();
		EmailRequestModel emailRequest = new EmailRequestModel();
		emailRequest.setTo(email);
		emailRequest.setSubject("Activation Link");
		String message = String.format("За да активирате акаунта си, моля кликнете на следният линк:%n" +
				"http://localhost:8080/auth/confirm-registration/%s%n" +
				"Линкът е валиден до 2 часа", aLink);
		emailRequest.setText(message);
		User savedUser = userRepository.save(user);
		this.emailService.sendSimpleEmail(emailRequest);
		return savedUser;
    }

	@Override
	//TESTED
	public boolean confirmRegistration(String aLink) throws UserException {
		//List<String> list = Arrays.asList(link.split("/"));
		//String aLink = list.get(list.size() - 1);
		User foundUser = this.userService.findByActivationLink(aLink);
		LocalDateTime maxTime = foundUser.getMaximumActivationLinkTime();
		if (!foundUser.isEnabled() && LocalDateTime.now().isBefore(maxTime) && foundUser != null) {
			foundUser.setEnabled(true);
			foundUser.setActivationLink("");
		}
		this.userRepository.save(foundUser);
		return true;
	}

	@Override
	public String resetPassword(String inputEmail) throws UserException {
		User user = this.userRepository.findByEmail(inputEmail);
		if(user == null) throw new UserException("No user with that name exists!");
		String uniqueString = UUID.randomUUID().toString();
		user.setUniqueStringForPasswordReset(uniqueString);
		user.setMaximumPasswordResetTime(LocalDateTime.now().plusHours(2));
		userRepository.save(user);

		String email = user.getEmail();
		EmailRequestModel emailRequest = new EmailRequestModel();
		emailRequest.setTo(email);
		emailRequest.setSubject("Reset Password");
		String link = String.format("http://localhost:8080/auth/create-new-password/%s", uniqueString);
		String message = String.format("За да сложете нова парола, моля кликнете на следният линк:%n" +
				"%s%n" +
				"Линкът е валиден до 2 часа", link);
		emailRequest.setText(message);
		this.emailService.sendSimpleEmail(emailRequest);
		return link;
	}

	@Override
	public boolean createNewPassword(String uniqueStringForPasswordReset, String password) throws UserException {
		User foundUser = this.userService.findByUniqueStringForResetPassword(uniqueStringForPasswordReset);
		LocalDateTime maxTimeForResetPassword = foundUser.getMaximumPasswordResetTime();
		LocalDateTime now = LocalDateTime.now();
		if(now.isAfter(maxTimeForResetPassword) || foundUser == null) {
			throw new UserException("The password change time has expired! / User can not be found!");
		}
		foundUser.setPassword(this.passwordEncoder.encode(password));
		foundUser.setUniqueStringForPasswordReset("");
		userRepository.save(foundUser);
		this.emailService.sendSimpleEmail(foundUser.getEmail(), "New password", "Your password was changed!");
		return true;
	}
}
