package robotic.system.loanComponent.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class LoanRequestDTO {
    private String componentName;
    private int quantity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date expectedReturnDate;
    private String borrowerEmail;
}
