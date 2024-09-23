package msTask.web.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msTask.constants.RegexConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestModel extends BaseRequestModel {
	
	@Pattern(regexp = RegexConstant.USERNAME_REGEX, message = RegexConstant.USERNAME_REGEX_MSG_ERR)
	private String username;
	
	@Pattern(regexp = RegexConstant.PASSWORD_REGEX, message = RegexConstant.PASSWORD_REGEX_MSG_ERR)
    private String password;
    
    @Email(message = RegexConstant.EMAIL_REGEX_MSG_ERR)
    private String email;
    
    @Pattern(regexp = RegexConstant.FIRST_NAME_REGEX, message = RegexConstant.FIRST_NAME_REGEX_MSG_ERR)
    private String firstName;
    
    @Pattern(regexp = RegexConstant.LAST_NAME_REGEX, message = RegexConstant.LAST_NAME_REGEX_MSG_ERR)
	private String lastName;
    
    @Past(message = RegexConstant.BIRTHDAY_REGEX_MSG_ERR)
	private LocalDate birthday;
    
    @Pattern(regexp = RegexConstant.PHONE_NUMBER_REGEX, message = RegexConstant.PHONE_NUMBER_REGEX_MSG_ERR)
	private String phoneNumber;

}