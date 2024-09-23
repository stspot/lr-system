package msTask.web.request;

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
public class NewPasswordRequestModel {

	@Pattern(regexp = RegexConstant.PASSWORD_REGEX, message = RegexConstant.PASSWORD_REGEX_MSG_ERR)
	private String password;
}
