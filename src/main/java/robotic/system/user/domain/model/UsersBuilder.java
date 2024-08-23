package robotic.system.user.domain.model;

import java.util.HashSet;
import java.util.Set;

public class UsersBuilder {
    protected String name;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected String address;
    protected Set<Role> roles = new HashSet<>();

    public UsersBuilder() {}

    public UsersBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UsersBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UsersBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UsersBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UsersBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public UsersBuilder withRole(Role role) {
        this.roles.add(role);
        return this;
    }

    public UsersBuilder withRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Users build() {
        return new Users(this);
    }
}
