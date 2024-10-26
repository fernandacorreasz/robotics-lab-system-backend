package robotic.system.inventory.domain.dto;

import java.util.UUID;

public interface ComponentDTO {
    UUID getId();
    String getComponentId();
    String getName();
    String getSerialNumber();
    String getDescription();
    Integer getQuantity();
}
