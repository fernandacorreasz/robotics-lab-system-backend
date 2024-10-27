package robotic.system.loanComponent.domain.dto;

import lombok.Getter;
import lombok.Setter;
import robotic.system.loanComponent.domain.en.LoanStatus;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class LoanComponentDTO {
    private UUID id;
    private String loanId;
    
    private UUID borrowerId;
    private String borrowerEmail;
    
    private UUID authorizerId;
    private String authorizerEmail;
    
    private UUID componentId;
    private String componentName;
    
    private Date loanDate;
    private Date expectedReturnDate;
    private Date actualReturnDate;
    private LoanStatus status;
    private int quantity;

    public LoanComponentDTO(UUID id, String loanId, UUID borrowerId, String borrowerEmail, UUID authorizerId, String authorizerEmail, 
                            UUID componentId, String componentName, Date loanDate, Date expectedReturnDate, 
                            Date actualReturnDate, LoanStatus status, int quantity) {
        this.id = id;
        this.loanId = loanId;
        this.borrowerId = borrowerId;
        this.borrowerEmail = borrowerEmail;
        this.authorizerId = authorizerId;
        this.authorizerEmail = authorizerEmail;
        this.componentId = componentId;
        this.componentName = componentName;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
        this.quantity = quantity;
    }
}
