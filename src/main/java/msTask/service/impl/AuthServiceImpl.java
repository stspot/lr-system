package msTask.service.impl;

import static msTask.constants.CommonConstants.*;
import static msTask.constants.ExceptionConstants.*;

import java.time.LocalDateTime;
import java.util.UUID;
import msTask.service.EmailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import msTask.data.entity.Role;
import msTask.data.entity.User;
import msTask.data.repositority.RoleRepository;
import msTask.data.repositority.UserRepository;
import msTask.enums.RoleEnum;
import msTask.exception.UserException;
import msTask.models.EmailModel;
import msTask.security.jwt.JwtProvider;
import msTask.service.AuthService;
import msTask.service.UserService;
import msTask.web.response.AuthResponseModel;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final UserService userService;
	private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
	private final EmailService emailService;

	@Override
    public AuthResponseModel login(User user) throws UserException {
        User foundByEmail = this.userService.getByEmail(user.getEmail());
        if (!foundByEmail.isEnabled()) {
            throw new DisabledException("User is disabled");
        }
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    	String jwtToken = jwtProvider.generateToken(foundByEmail);
    	return new AuthResponseModel(jwtToken);
    }

	@Override
    public User register(User user) throws UserException {
		Role role;
    	if(userRepository.count() == 0) {
    		role = roleRepository.findByName(RoleEnum.ROLE_ADMIN.name());
    	} else {
    		role = roleRepository.findByName(RoleEnum.ROLE_USER.name());
    	}
		if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
			user.setUsername(user.getEmail().split("@")[0] + UUID.randomUUID().toString().substring(0,3)); //TODO...
		}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(role);
		user.setEnabled(false);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		String aLink = UUID.randomUUID().toString();
		user.setActivationLink(aLink);
		user.setMaximumActivationLinkTime(LocalDateTime.now().plusHours(MAXIMUM_ACCOUNT_CONFIRM_TIME));

		String email = user.getEmail();
		EmailModel emailRequest = new EmailModel();
		emailRequest.setTo(email);
		emailRequest.setSubject(CONFIRM_TITLE_EMAIL);
		String message = String.format(CONFIRM_MESSAGE, aLink);
		emailRequest.setText(message);

		boolean isUserExistWithEmail = this.userService.isUserExistWithEmail(email);
		if (isUserExistWithEmail) {
            throw new UserException(String.format(USER_WITH_EMAIL_ALREADY_EXISTS, email));
        }

		User savedUser = userRepository.save(user);
		this.emailService.sendSimpleEmail(emailRequest);
		return savedUser;
    }

	@Override
	//TESTED
	public boolean confirmRegistration(String aLink) throws UserException {
		User foundUser = this.userService.findByActivationLink(aLink);
		LocalDateTime maxTime = foundUser.getMaximumActivationLinkTime();
		if (!foundUser.isEnabled() && LocalDateTime.now().isBefore(maxTime) && foundUser != null) {
			foundUser.setEnabled(true);
			foundUser.setActivationLink("");
		}
		this.userRepository.save(foundUser);
		this.emailService.sendSimpleEmail(foundUser.getEmail(), ACCOUNT_ACTIVATED_TITLE_EMAIL, ACCOUNT_ACTIVATED_TEXT_EMAIL);
		return true;
	}

	@Override
	public String resetPassword(String inputEmail) throws UserException {
		User user = this.userRepository.findByEmail(inputEmail);
		if(user == null) throw new UserException(String.format(THERE_IS_NO_USER_WITH_THIS_EMAIL, inputEmail));
		String uniqueString = UUID.randomUUID().toString();
		user.setUniqueStringForPasswordReset(uniqueString);
		user.setMaximumPasswordResetTime(LocalDateTime.now().plusHours(MAXIMUM_RESET_PASSWORD_TIME));
		userRepository.save(user);

		String email = user.getEmail();
		EmailModel emailRequest = new EmailModel();
		emailRequest.setTo(email);
		emailRequest.setSubject(RESET_PASSWORD_TITLE_EMAIL);
		String message = String.format(RESET_PASSWORD_MESSAGE, uniqueString);
		emailRequest.setText(message);

		this.emailService.sendSimpleEmail(emailRequest);
		return CHECK_YOUR_EMAIL;
	}

	@Override
	public boolean createNewPassword(String uniqueStringForPasswordReset, String password) throws UserException {
		User foundUser = this.userService.findByUniqueStringForResetPassword(uniqueStringForPasswordReset);
		LocalDateTime maxTimeForResetPassword = foundUser.getMaximumPasswordResetTime();
		LocalDateTime now = LocalDateTime.now();
		if(now.isAfter(maxTimeForResetPassword) || foundUser == null) {
			throw new UserException(THE_PASSWORD_EXPIRED_OR_USER_DOES_NOT_EXIST);
		}
		foundUser.setPassword(this.passwordEncoder.encode(password));
		foundUser.setUniqueStringForPasswordReset("");
		userRepository.save(foundUser);
		this.emailService.sendSimpleEmail(foundUser.getEmail(), NEW_PASSWORD_TITLE_EMAIL, NEW_PASSWORD_TEXT_EMAIL);
		return true;
	}
}
