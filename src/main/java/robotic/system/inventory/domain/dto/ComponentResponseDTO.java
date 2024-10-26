package robotic.system.inventory.domain.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentResponseDTO {
    private UUID id;
    private String name;

    public ComponentResponseDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
