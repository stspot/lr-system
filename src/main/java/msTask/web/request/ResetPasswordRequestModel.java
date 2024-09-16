package msTask.web.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msTask.config.RegexConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequestModel {

	@Email(message = RegexConstant.EMAIL_REGEX_MSG_ERR)
	private String email;
}
