package robotic.system.user.domain.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Set<String> roles;

    public UserDTO(String name, String email, String phoneNumber, String address, Set<String> roles) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = roles;
    }
}
