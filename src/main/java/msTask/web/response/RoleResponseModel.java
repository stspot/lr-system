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
public class RoleResponseModel extends BaseResponseModel {

    private String name;
    private List<AuthorityResponseModel> authorities;
}
