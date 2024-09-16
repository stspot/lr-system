package msTask.data.entity;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {

	private static final long serialVersionUID = -8874681201650076381L;
	
	@NotBlank
    @Size(max = 50)
    @Column(unique = true, nullable = false)
    private String name;

	@Override
	public String getAuthority() {
		return this.name;
	}
}
