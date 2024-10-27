package robotic.system.loanComponent.domain.dto;

import lombok.Getter;
import lombok.Setter;
import robotic.system.loanComponent.domain.en.LoanStatus;

@Getter
@Setter
public class LoanAuthorizationDTO {
    private String loanId;
    private LoanStatus status;
    private int authorizedQuantity;
    private String authorizerEmail;
}
