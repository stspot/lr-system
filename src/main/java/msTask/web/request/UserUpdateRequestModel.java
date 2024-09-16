package msTask.web.request;

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
public class UserUpdateRequestModel {
    
    @Pattern(regexp = RegexConstant.FIRST_NAME_REGEX, message = RegexConstant.FIRST_NAME_REGEX_MSG_ERR)
    private String firstName;
    
    @Pattern(regexp = RegexConstant.LAST_NAME_REGEX, message = RegexConstant.LAST_NAME_REGEX_MSG_ERR)
	private String lastName;
    
    @Pattern(regexp = RegexConstant.PHONE_NUMBER_REGEX, message = RegexConstant.PHONE_NUMBER_REGEX_MSG_ERR)
	private String phoneNumber;

}
