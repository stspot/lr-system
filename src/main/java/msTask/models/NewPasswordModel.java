package msTask.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msTask.web.request.BaseRequestModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordModel extends BaseRequestModel {

	private String password;
	private String uniqueStringForPasswordReset;
}
