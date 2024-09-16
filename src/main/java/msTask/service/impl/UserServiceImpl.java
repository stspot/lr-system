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
	public User findByUniqueStringForResetPassword(String uniqueStringForPasswordReset) throws UserException {
		User foundUser = userRepository.findByUniqueStringForPasswordReset(uniqueStringForPasswordReset);
		if(foundUser == null) throw new UserException("User not found");
		return foundUser;
	}

	@Override
	public User findByActivationLink(String aLink) throws UserException {
		User foundUser = userRepository.findByActivationLink(aLink);
		if(foundUser == null) throw new UserException("User not found");
		return foundUser;
	}

	@Override
	public User findById(String id) throws UserException {
		User foundUser = userRepository.findById(id).orElse(null);
		if(foundUser == null) throw new UserException("User not found");
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
		User foundUser = userRepository.findByUsername(username);
		if(foundUser == null) throw new UserException("User not found");
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
}
