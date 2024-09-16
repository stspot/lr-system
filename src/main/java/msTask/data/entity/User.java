package msTask.data.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = -8346827525781557058L;

    @Size(min = 3, max = 50)
    @Column(unique = true, nullable = false)
    private String username;

    @Size(min = 6, max = 100)
    private String password;

    @Email
    @Size(max = 100)
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
	private String lastName;
    
    @PastOrPresent
    @Column(name = "birthday", nullable = false, columnDefinition = "DATE DEFAULT '2000-01-01'")
	private LocalDate birthday = LocalDate.MIN;
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @Column(name = "enabled")
    private boolean enabled = false;

    @Column(name = "unique_string_for_password_reset", nullable = true)
    private String uniqueStringForPasswordReset;

    @Column(name = "maximum_password_reset_time", nullable = true)
    private LocalDateTime maximumPasswordResetTime;

    @Column(name = "activation_link", nullable = true)
    private String activationLink;

    @Column(name = "maximum_activation_link_time", nullable = true)
    private LocalDateTime maximumActivationLinkTime;

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.addAll(role.getAuthorities());
        }
        return authorities;
    }
}
