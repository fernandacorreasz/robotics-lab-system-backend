package robotic.system.user.domain.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String roleId;
    
    private String nameRole;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users;

    public Role() {
        this.roleId = UUID.randomUUID().toString();
    }

    Role(RoleBuilder builder) {
        this.id = UUID.randomUUID();
        this.roleId = UUID.randomUUID().toString(); 
        this.nameRole = builder.nameRole;
    }
}
