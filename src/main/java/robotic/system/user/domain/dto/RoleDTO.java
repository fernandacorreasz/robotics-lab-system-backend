package robotic.system.user.domain.dto;

import org.hibernate.validator.constraints.UUID;

public record RoleDTO(UUID id, String nameRole) {
}
