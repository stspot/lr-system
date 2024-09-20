package msTask.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel extends BaseResponseModel {

    private String username;
    private String email;
    private List<RoleResponseModel> roles;
    private String firstName;
	private String lastName;
	private String birthday;
	private String phoneNumber;

}
