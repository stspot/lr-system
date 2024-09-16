package msTask.service;

import msTask.data.entity.User;
import msTask.exception.UserException;
import msTask.web.response.AuthResponseModel;

public interface AuthService {

    /**
     * Logs in the user with the provided login data (email and password) and returns an authentication response model.
     *
     * @param loginData The user login data.
     * @return The authentication response model containing the generated token.
     * @throws UserException If there is an error during the login process.
     */
    AuthResponseModel login(User loginData) throws UserException;

    /**
     * Registers a new user and returns the saved user.
     *
     * @param user The user to register.
     * @return The saved user.
     * @throws UserException If there is an error during the registration process.
     */
    User register(User user) throws UserException;

	/**
     * Resets the password for the user with the given email.
     *
     * @param email The email of the user.
     * @return The reset password link.
     * @throws UserException If there is an error during the password reset process.
     */
    String resetPassword(String email) throws UserException;

    /**
     * Confirms the registration of a user using the provided link.
     *
     * @param link The registration link.
     * @return true if the registration is confirmed, false otherwise.
     * @throws UserException If there is an error during the registration confirmation process.
     */
    boolean confirmRegistration(String link) throws UserException;

    /**
     * Creates a new password for the user with the provided unique string for password reset.
     *
     * @param uniqueStringForPasswordReset The unique string for password reset.
     * @param password The new password to set.
     * @return {@code true} if the password is created successfully, {@code false} otherwise.
     * @throws UserException If there is an error during the password creation process.
     */
    boolean createNewPassword(String uniqueStringForPasswordReset, String password) throws UserException;
}
