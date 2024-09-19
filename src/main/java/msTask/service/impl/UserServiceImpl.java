package msTask.service.impl;

import msTask.exception.UserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import msTask.data.entity.User;
import msTask.data.repositority.UserRepository;
import msTask.service.UserService;

import static msTask.config.ExceptionConstants.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    @Override
    public User getByEmail(String email) {
    	User userByEmail = userRepository.findByEmail(email);
		if(userByEmail == null) throw new UsernameNotFoundException(email);
		return userByEmail;
	}

	@Override
	public User getUserDetailsByEmail(String email) {
		User userByEmail = userRepository.findByEmail(email);
		if(userByEmail == null) throw new UsernameNotFoundException(email);
		return userByEmail;
	}

	@Override
	public boolean deleteUserById(String userId) {
		this.userRepository.deleteById(userId);
		return true;
	}
	
	@Override
	public boolean deleteUserByIdFake(String userId) throws UserException {
		User foundUser = this.findById(userId);
		foundUser.setEnabled(false);
		this.userRepository.save(foundUser);
		return true;
	}

	@Override
	public User findByUniqueStringForResetPassword(String uniqueStringForPasswordReset) throws UserException {
		User foundUser = userRepository.findByUniqueStringForPasswordReset(uniqueStringForPasswordReset);
		if(foundUser == null) throw new UserException(String.format(USER_WITH_USFPR_NOT_FOUND, uniqueStringForPasswordReset));
		return foundUser;
	}

	@Override
	public User findByActivationLink(String confirmLink) throws UserException {
		User foundUser = userRepository.findByActivationLink(confirmLink);
		if(foundUser == null) throw new UserException(String.format(USER_WITH_CONFIRM_LINK_NOT_FOUND, confirmLink));
		return foundUser;
	}

	@Override
	public User findById(String id) throws UserException {
		User foundUser = userRepository.findById(id).orElse(null);
		if(foundUser == null) throw new UserException(String.format(USER_WITH_ID_NOT_FOUND, id));
		return foundUser;
	}

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public Page<User> findAllP(Pageable pageable) {
        return this.userRepository.findAll(pageable);
	}

	@Override
	public User findByUsername(String username) throws UserException {
		User foundUser = userRepository.findByUsername(username).orElse(null);
		if(foundUser == null) throw new UserException(String.format(USER_WITH_USERNAME_NOT_FOUND, username));
		return foundUser;
	}

	@Override
	public User updateUser(User newUser) throws UserException {
		User fUser = this.findById(newUser.getId());
		fUser.setFirstName(newUser.getFirstName());
		fUser.setLastName(newUser.getLastName());
		fUser.setPhoneNumber(newUser.getPhoneNumber());
		return this.userRepository.save(fUser);
	}

	@Override
	public boolean isUserExistWithEmail(String email) {
		return this.userRepository.findByEmail(email) != null;
	}
}
