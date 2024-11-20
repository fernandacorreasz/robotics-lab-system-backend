package robotic.system.loanComponent.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import robotic.system.inventory.domain.Component;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Entity
public class LoanComponent {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id", referencedColumnName = "id")
    @JsonIgnore // Break the cycle by ignoring borrower serialization
    private Users borrower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorizer_id", referencedColumnName = "id")
    @JsonIgnore
    private Users authorizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", referencedColumnName = "id")
    @JsonIgnore
    private Component component;

    @Temporal(TemporalType.TIMESTAMP)
    private Date loanDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expectedReturnDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date actualReturnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_authorizer_id", referencedColumnName = "id")
    @JsonIgnore
    private Users returnAuthorizer;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private int quantity;

    public LoanComponent() {
    }

    LoanComponent(LoanComponentBuilder builder) {
        this.id = UUID.randomUUID();
        this.loanId = UUID.randomUUID().toString();
        this.borrower = builder.borrower;
        this.authorizer = builder.authorizer;
        this.component = builder.component;
        this.loanDate = builder.loanDate;
        this.expectedReturnDate = builder.expectedReturnDate;
        this.actualReturnDate = builder.actualReturnDate;
        this.returnAuthorizer = builder.returnAuthorizer;
        this.status = builder.status;
        this.quantity = builder.quantity;
    }
}
