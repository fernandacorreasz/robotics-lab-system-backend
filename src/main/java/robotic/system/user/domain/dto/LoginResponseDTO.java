package robotic.system.user.domain.dto;

import java.util.UUID;

public record LoginResponseDTO(String token, int permissionLevel, String name, UUID userId) {
}