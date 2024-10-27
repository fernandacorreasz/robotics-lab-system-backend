package robotic.system.loanComponent.domain.model;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class LoanHistory {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String loanId;
    private String status;
    private String borrowerName;
    private String authorizerName;
    private String componentName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;

    public LoanHistory() {
        this.loanId = UUID.randomUUID().toString();
    }
}
