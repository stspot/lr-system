package msTask.service;

import msTask.data.entity.User;
import msTask.exception.UserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
	
	/**
	 * Retrieves the user details based on the email address.
	 *
	 * @param email The email address of the user.
	 * @return The user details matching the email address.
	 */
	User getUserDetailsByEmail(String email);

	/**
	 * Retrieves the user details based on the email address.
	 *
	 * @param email The email address of the user.
	 * @return The user details matching the email address.
	 */
	User getByEmail(String email);
	
	/**
	 * Deletes a user by their ID.
	 *
	 * @param userId The ID of the user to delete.
	 * @return true if the user is deleted, false otherwise.
	 */
	boolean deleteUserById(String userId);
	
	/**
	 * Deletes a user by their ID (fake implementation).
	 *
	 * @param userId The ID of the user to delete.
	 * @return true if the user is deleted, false otherwise.
	 * @throws UserException if there is an error during the deletion process.
	 */
	boolean deleteUserByIdFake(String userId) throws UserException;

    /**
	 * Finds a user by their unique string for password reset.
	 *
	 * @param uniqueStringForPasswordReset The unique string for password reset of the user.
	 * @return The user matching the unique string for password reset.
	 * @throws UserException if there is an error retrieving the user or if the password reset time expired.
	 */
	User findByUniqueStringForResetPassword(String uniqueStringForPasswordReset) throws UserException;

	/**
	 * Finds a user by their activation link.
	 *
	 * @param aLink The activation link of the user.
	 * @return The user matching the activation link.
	 * @throws UserException if there is an error retrieving the user.
	 */
	User findByActivationLink(String aLink) throws UserException;

	/**
	 * Finds a user by their ID.
	 *
	 * @param id The ID of the user to find.
	 * @return The user matching the given ID.
	 * @throws UserException if there is an error retrieving the user.
	 */
	User findById(String id) throws UserException;

	/**
	 * Retrieves a list of all users in the database.
	 *
	 * @return A list of all users.
	 */
	List<User> findAll();

	/**
	 * Retrieves a page of users from the database based on the given pageable parameters.
	 *
	 * @param pageable The pageable object specifying the page number and size.
	 * @return A page of users.
	 */
	Page<User> findAllP(Pageable pageable);

	/**
	 * Searches for users based on the given search term, birthday, and pagination parameters.
	 *
	 * @param searchTerm The term to search for in the username or email fields of the users.
	 * @param pageable   The pagination parameters specifying the page number and size.
	 * @return A page of users matching the search term and birthday.
	 */
	Page<User> searchUsers(String searchTerm, Pageable pageable);

	/**
	 * Finds a user by their username.
	 *
	 * @param username The username of the user to find.
	 * @return The user matching the given username.
	 * @throws UserException if there is an error retrieving the user.
	 */
	User findByUsername(String username) throws UserException;

	/**
	 * Updates the user with the information from the provided new user object.
	 *
	 * @param newUser The new user object containing the updated information.
	 * @return The updated user object.
	 * @throws UserException if there is an error updating the user.
	 */
	User updateUser(User newUser) throws UserException;

    /**
	 * Checks if a user with the given email address already exists in the system.
	 *
	 * @param email The email address to check.
	 * @return true if a user with the given email address exists, false otherwise.
	 */
	boolean isUserExistWithEmail(String email);
}
