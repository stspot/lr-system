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
public class BaseRequestModel {
	
	@Pattern(regexp = RegexConstant.ID_REGEX, message = RegexConstant.ID_REGEX_MSG_ERR)
	private String id;
}
