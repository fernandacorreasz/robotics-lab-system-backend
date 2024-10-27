package robotic.system.loanComponent.domain.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoanReturnDTO {
    private UUID loanId;
    private int returnedQuantity;
    private String borrowerEmail;
}
