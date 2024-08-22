package robotic.system.user.domain.dto;

import java.util.Set;

public record RegisterDTO(String name, String email, String password, Set<String> roles) {
}
