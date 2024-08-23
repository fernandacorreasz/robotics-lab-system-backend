package robotic.system.user.domain.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String userId;
    private String name;
    private String email;
    private String password;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    private String phoneNumber;
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "UserRole",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public Users() {
    }
    
    Users(UsersBuilder builder) {
        this.id = UUID.randomUUID();  
        this.userId = UUID.randomUUID().toString(); 
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.registrationDate = new Date(); 
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
        this.roles = builder.roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role::getNameRole)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
