package robotic.system.loanComponent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComponentWithLoanDetailsDTO {
    private UUID id;
    private String name;
    private String serialNumber;
    private String description;
    private int totalQuantity;
    private int requestedQuantity = 0;
    private int authorizedQuantity = 0;
    private int borrowedQuantity = 0;
    private int availableQuantity = 0;

    public ComponentWithLoanDetailsDTO(UUID id, String name, String serialNumber, String description, int totalQuantity) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.description = description;
        this.totalQuantity = totalQuantity;
    }
}
