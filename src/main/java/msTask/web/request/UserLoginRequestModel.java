package msTask.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msTask.config.RegexConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestModel {
	
	@Email(message = RegexConstant.EMAIL_REGEX_MSG_ERR)
	private String email;
	
	@Pattern(regexp = RegexConstant.PASSWORD_REGEX, message = RegexConstant.PASSWORD_REGEX_MSG_ERR)
	private String password;
}
