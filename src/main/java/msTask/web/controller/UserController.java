package msTask.web.controller;

import msTask.config.RegexConstant;
import msTask.data.entity.User;
import msTask.exception.UserException;
import msTask.web.request.UserUpdateRequestModel;
import msTask.web.response.UserResponseModel;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

import msTask.service.UserService;
import static msTask.config.PathConstants.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = USERS_L)
//@CrossOrigin(originPatterns = CROSS_ORGIN_PATTERN_L)
@Tag(name = "User Controller", description = "")
public class UserController {
	
	private final UserService userService;
	private final ModelMapper modelMapper;

	/**
	 * Retrieves a paginated list of users with specified page number and page size.
	 *
	 * @param page              The page number for pagination.
	 *                          Value must be between 0 and 100 (inclusive).
	 *                          Default value is 0.
	 * @param size              The number of users per page.
	 *                          Value must be between 5 and 20 (inclusive).
	 *                          Default value is 10.
	 * @param primarySortField  The primary sort field for the users.
	 *                          Default value is "lastName".
	 * @param secondarySortField The secondary sort field for the users.
	 *                          Default value is "birthday".
	 * @param isAscPrimary      Flag indicating whether the primary sort is in ascending order or not.
	 *                          Default value is true.
	 * @param isAscSecondary    Flag indicating whether the secondary sort is in ascending order or not.
	 *                          Default value is true.
	 * @return A ResponseEntity containing a Page of UserResponseModel objects.
	 */
	@GetMapping(path = GET_ALL_USERS_BY_PAGES_NA_L)
	public ResponseEntity<Page<UserResponseModel>> getAllUsersPagesNa(
			@DecimalMin(value = "0")
			@DecimalMax(value = "100")
			@RequestParam(defaultValue = "0") int page,
			@DecimalMin(value = "5")
			@DecimalMax(value = "20")
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "lastName") String primarySortField,
			@RequestParam(defaultValue = "birthday") String secondarySortField,
			@RequestParam(defaultValue = "true") boolean isAscPrimary,
			@RequestParam(defaultValue = "true") boolean isAscSecondary
	) {
		//TODO... Params Validation!!!
		Pageable pageable = PageRequest.of(page, size);
		Page<User> all = userService.findAllUsersNq(pageable, primarySortField, secondarySortField, isAscPrimary, isAscSecondary);
		Page<UserResponseModel> userResponsePage = all.map(user -> modelMapper.map(user, UserResponseModel.class));
		return new ResponseEntity<>(userResponsePage, HttpStatus.OK);
	}

	/**
	 * Retrieves a paginated list of users with specified page number and page size.
	 *
	 * @param page The page number for pagination.
	 * @param size The number of users per page.
	 * @return A ResponseEntity containing a Page of UserResponseModel objects.
	 * @deprecated This method is deprecated. Use searchUsers method instead.
	 * @deprecated For new projects prefer to use new API methods.
	 * @deprecated Use searchUsers method instead.
	 * @deprecated Use searchUsers method instead.
	 */
	@Deprecated
	@Operation(
			summary = "Get paginated list of users",
			description = "Fetches a paginated list of users with specified page number and page size.",
			parameters = {
					@Parameter(name = "page", description = "Page number for pagination", example = "0"),
					@Parameter(name = "size", description = "Number of users per page", example = "10")})
	@ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list of users")
	@ApiResponse(responseCode = "400", description = "Invalid page or size parameter")
	@GetMapping(path = GET_ALL_USERS_BY_PAGES_L)
	public ResponseEntity<Page<UserResponseModel>> getAllUsersPages(
			@DecimalMin(value = "0")
			@DecimalMax(value = "100")
			@RequestParam(defaultValue = "0") int page,
			@DecimalMin(value = "5")
			@DecimalMax(value = "20")
			@RequestParam(defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<User> all = userService.findAllP(pageable);
		Page<UserResponseModel> userResponsePage = all.map(user -> modelMapper.map(user, UserResponseModel.class));
		return new ResponseEntity<>(userResponsePage, HttpStatus.OK);
	}

	/**
	 * Retrieves a page of users based on the search criteria.
	 *
	 * @param searchTerm The search term to match against the username and email fields of the users.
	 * @param birthday The birthday to filter the users by. Only users with a matching birthday will be included.
	 * @param pageable The pagination information.
	 * @return A ResponseEntity containing a page of UserResponseModel objects.
	 */
	@GetMapping("/search")
	public ResponseEntity<Page<UserResponseModel>> searchUsers(
			//TODO... Params Validation!!!
			@RequestParam(required = false) String searchTerm,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthday,
			@PageableDefault(size = 10) Pageable pageable) {
		Page<User> users = userService.searchUsers(searchTerm, birthday, pageable);
		Page<UserResponseModel> userResponsePage = users.map(user -> modelMapper.map(user, UserResponseModel.class));
		return new ResponseEntity<>(userResponsePage, HttpStatus.OK);
	}
	
	/**
	 * Retrieves a list of all users.
	 *
	 * @return A ResponseEntity containing a list of UserResponseModel objects.
	 */
	@Operation(summary = "Get list of all users",
			description = "Fetches a list of all users.")
	@ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
	@ApiResponse(responseCode = "204", description = "No users found")
	@GetMapping(GET_ALL_USERS_L)
	public ResponseEntity<List<UserResponseModel>> getAllUsers() {
		List<User> users = this.userService.findAll();
		List<UserResponseModel> allUsers = users.stream().map(u -> this.modelMapper.map(u, UserResponseModel.class))
				.toList();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
	
	/**
	 * Retrieves a user by their user ID.
	 *
	 * @param userId The ID of the user to retrieve.
	 * @return A ResponseEntity containing the UserResponseModel object.
	 * @throws UserException if there is an error retrieving the user.
	 */
	@GetMapping(path = GET_ALL_USER_BY_ID_L)
	@Operation(summary = "Get user by ID",
			description = "Fetches a user by their ID.",
			parameters = @Parameter(name = "userId", description = "ID of the user to be retrieved", example = "12345"))
	@ApiResponse(responseCode = "200", description = "Successfully retrieved user")
	@ApiResponse(responseCode = "404", description = "User not found")
	@ApiResponse(responseCode = "400", description = "Invalid user ID format")
	public ResponseEntity<UserResponseModel> getByUserId(
			@Pattern(regexp = RegexConstant.ID_REGEX, message = RegexConstant.ID_REGEX_MSG_ERR)
			@PathVariable ("userId") String userId) throws UserException {
		User fUser = this.userService.findById(userId);
		UserResponseModel user = this.modelMapper.map(fUser, UserResponseModel.class);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	/**
	 * Updates a user with the information from the provided user update request model.
	 *
	 * @param uurm The user update request model containing the updated user information.
	 * @return A ResponseEntity containing the updated UserResponseModel object.
	 * @throws UserException if there is an error updating the user.
	 */
	@PatchMapping(path = UPDATE_USER_L)
	@Operation(summary = "Update user information",
			description = "Updates user details based on provided data.")
	@ApiResponse(responseCode = "200", description = "User successfully updated")
	@ApiResponse(responseCode = "400", description = "Invalid input data")
	@ApiResponse(responseCode = "404", description = "User not found")
	public ResponseEntity<UserResponseModel> updateUser(@Valid @RequestBody UserUpdateRequestModel uurm) throws UserException {
		User newUser = this.modelMapper.map(uurm, User.class);
		UserResponseModel updatedUser = this.modelMapper.map(this.userService.updateUser(newUser), UserResponseModel.class);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	/**
	 * Deletes a user by their ID.
	 *
	 * @param userId The ID of the user to delete. Must be a string containing only alphanumeric characters and hyphens.
	 * @return A ResponseEntity containing a boolean value indicating whether the user is deleted or not.
	 * @throws UserException If there is an error deleting the user.
	 */
	@DeleteMapping("/delete/{userId}")
	@Operation(summary = "Delete a user",
			description = "Deletes a user by their ID.",
			parameters = @Parameter(name = "userId", description = "ID of the user to be deleted", example = "12345"))
	@ApiResponse(responseCode = "200", description = "User successfully deleted")
	@ApiResponse(responseCode = "404", description = "User not found")
	@ApiResponse(responseCode = "400", description = "Invalid user ID format")
	public ResponseEntity<Boolean> deleteUser(
			@Pattern(regexp = RegexConstant.ID_REGEX, message = RegexConstant.ID_REGEX_MSG_ERR)
			@PathVariable ("userId") String userId) throws UserException {
		boolean isDeleted = this.userService.deleteUserById(userId);
		return new ResponseEntity<>(isDeleted, HttpStatus.OK);
	}
}
