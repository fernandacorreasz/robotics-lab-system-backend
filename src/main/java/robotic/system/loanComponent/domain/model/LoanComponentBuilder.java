package robotic.system.loanComponent.domain.model;

import robotic.system.inventory.domain.model.Component;
import robotic.system.user.domain.model.Users;

import java.util.Date;

public class LoanComponentBuilder {
    protected Users borrower;
    protected Users authorizer;
    protected Component component;
    protected Date loanDate;
    protected Date expectedReturnDate;
    protected Date actualReturnDate;
    protected Users returnAuthorizer;
    protected String status;

    public LoanComponentBuilder withBorrower(Users borrower) {
        this.borrower = borrower;
        return this;
    }

    public LoanComponentBuilder withAuthorizer(Users authorizer) {
        this.authorizer = authorizer;
        return this;
    }

    public LoanComponentBuilder withComponent(Component component) {
        this.component = component;
        return this;
    }

    public LoanComponentBuilder withLoanDate(Date loanDate) {
        this.loanDate = loanDate;
        return this;
    }

    public LoanComponentBuilder withExpectedReturnDate(Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
        return this;
    }

    public LoanComponentBuilder withActualReturnDate(Date actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
        return this;
    }

    public LoanComponentBuilder withReturnAuthorizer(Users returnAuthorizer) {
        this.returnAuthorizer = returnAuthorizer;
        return this;
    }

    public LoanComponentBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public LoanComponent build() {
        return new LoanComponent(this);
    }
}
