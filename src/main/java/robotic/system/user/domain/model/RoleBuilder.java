package robotic.system.user.domain.model;

import java.util.UUID;

public class RoleBuilder {
    protected String nameRole;

    public RoleBuilder() {}

    public RoleBuilder withNameRole(String nameRole) {
        this.nameRole = nameRole;
        return this;
    }

    public Role build() {
        Role role = new Role(this);
        role.setRoleId(UUID.randomUUID().toString());
        return role;
    }
}
