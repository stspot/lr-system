package msTask.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import msTask.web.request.NewPasswordRequestModel;
import msTask.web.request.ResetPasswordRequestModel;

import msTask.web.response.ResetPasswordResponseModel;

import static msTask.constants.PathConstants.*;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import msTask.constants.RegexConstant;
import msTask.data.entity.User;
import msTask.exception.UserException;
import msTask.service.AuthService;
import msTask.web.request.UserLoginRequestModel;
import msTask.web.request.UserRegisterRequestModel;
import msTask.web.response.AuthResponseModel;
import msTask.web.response.UserResponseModel;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AUTH_L)
//@CrossOrigin(originPatterns = CROSS_ORGIN_PATTERN_L)
@Tag(name = "Auth Controller", description = "This controller handles the registration, " +
		"login and forgotten password recovery operations in this api.")
public class AuthController {

	private final AuthService authService;
	private final ModelMapper modelMapper;
	
	/**
	 * Logs in the user with the provided login data (email and password) and returns an authentication response model.
	 *
	 * @param ulrm The UserLoginRequestModel object containing the user login data.
	 * @return A ResponseEntity object containing the authentication response model with the generated token.
	 * @throws UserException If there is an error during the login process.
	 */
	@Operation(summary = "User login", description = "Authenticates a user and returns a token.")
	@ApiResponse(responseCode = "200", description = "User successfully authenticated")
	@ApiResponse(responseCode = "400", description = "Invalid login credentials")
	@ApiResponse(responseCode = "401", description = "Unauthorized access")
	@PostMapping(path = LOGIN_L)
    public ResponseEntity<AuthResponseModel> login(@Valid @RequestBody UserLoginRequestModel ulrm) throws UserException {
		User ulsm = this.modelMapper.map(ulrm, User.class);
    	AuthResponseModel authResponseModel = this.authService.login(ulsm);
    	return new ResponseEntity<>(authResponseModel, HttpStatus.OK);
    }

	/**
	 * Registers a new user and returns the saved user.
	 *
	 * @param inputUser The UserRegisterRequestModel object containing the user registration data.
	 * @return A ResponseEntity object containing the UserResponseModel object representing the saved user.
	 * @throws UserException If there is an error during the registration process.
	 */
	@Operation(summary = "Register new user", description = "Registers a new user.")
	@ApiResponse(responseCode = "201", description = "User successfully registered")
	@ApiResponse(responseCode = "400", description = "Invalid input data")
	@ApiResponse(responseCode = "409", description = "User already exists")
	@PostMapping(path = REGISTER_L)
	public ResponseEntity<UserResponseModel> register(@Valid @RequestBody UserRegisterRequestModel inputUser)
			throws UserException {
		User userForSave = this.modelMapper.map(inputUser, User.class);
		User savedUser = this.authService.register(userForSave);
		return new ResponseEntity<>(
				this.modelMapper.map(savedUser, UserResponseModel.class),
				HttpStatus.CREATED);
	}
	
	/**
	 * Confirms the registration of a user using the provided link.
	 *
	 * @param aLink The registration link.
	 * @return A ResponseEntity object containing a boolean value indicating if the registration is confirmed (true) or not (false).
	 * @throws UserException If there is an error during the registration confirmation process.
	 */
	@GetMapping(path = CONFIRM_REGISTRATION_L)
	@Operation(summary = "Confirm user registration", description = "Validates the registration confirmation link and activates the user's account.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registration confirmed successfully", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Invalid or malformed registration link", content = @Content),
			@ApiResponse(responseCode = "404", description = "Registration link not found or expired", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
	public ResponseEntity<Boolean> confirmRegistration (
			@Parameter(description = "Unique registration confirmation link", required = true)
			@Pattern(regexp = RegexConstant.ID_REGEX, message = RegexConstant.ID_REGEX_MSG_ERR)
			@PathVariable ("aLink") String aLink) throws UserException {
		boolean isConfirmed = authService.confirmRegistration(aLink);
		return new ResponseEntity<>(isConfirmed, HttpStatus.OK);
	}

	/**
	 * Reset the password for the user with the given email.
	 *
	 * @param rprm The ResetPasswordRequestModel object containing the user's email.
	 * @return A ResponseEntity object containing a String value representing the reset password link.
	 * @throws UserException If there is an error during the password reset process.
	 */
	@Operation(summary = "Reset user password", description = "Generates a password reset link and sends it to the user's email.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Password reset link generated successfully", content = @Content(mediaType = "text/plain")),
			@ApiResponse(responseCode = "400", description = "Invalid request or missing data", content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
	@PostMapping(path = RESET_PASSWORD_L)
	public ResponseEntity<ResetPasswordResponseModel> resetPassword (
			@Valid @RequestBody ResetPasswordRequestModel rprm) throws UserException {
		String message = this.authService.resetPassword(rprm.getEmail());
		ResetPasswordResponseModel responseModel = new ResetPasswordResponseModel();
		responseModel.setMessage(message);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Creates a new password for the user with the provided unique string for password reset.
	 *
	 * @param uniqueStringForPasswordReset The unique string for password reset.
	 * @param nprm The NewPasswordRequestModel object containing the new password.
	 * @return A ResponseEntity object containing a boolean value indicating if the password is created successfully (true) or not (false).
	 * @throws UserException If there is an error during the password creation process.
	 */
	@Operation(summary = "Create a new password",
			description = "Creates a new password by validating the unique reset string and accepting the new password.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Password created successfully",
					content = @Content(schema = @Schema(implementation = Boolean.class))),
			@ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
	@PostMapping(path = CREATE_NEW_PASSWORD_L)
	public ResponseEntity<Boolean> createNewPassword (
			@Parameter(description = "Unique password reset string link", required = true)
			@Pattern(regexp = RegexConstant.ID_REGEX, message = RegexConstant.ID_REGEX_MSG_ERR)
			@PathVariable ("uniqueStringForPasswordReset") String uniqueStringForPasswordReset,
			@Valid @RequestBody NewPasswordRequestModel nprm) throws UserException {
		boolean isNewPasswordCreated = this.authService.createNewPassword(uniqueStringForPasswordReset, nprm.getPassword());
		return new ResponseEntity<>(isNewPasswordCreated, HttpStatus.CREATED);
	}
}
