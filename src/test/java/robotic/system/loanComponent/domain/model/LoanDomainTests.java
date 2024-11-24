package robotic.system.loanComponent.domain.model;


import org.junit.jupiter.api.Test;
import robotic.system.inventory.domain.Component;
import robotic.system.loanComponent.domain.dto.*;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LoanDomainTests {

    @Test
    void testLoanComponentBuilder() {
        Users borrower = new Users();
        Users authorizer = new Users();
        Component component = new Component();
        Date loanDate = new Date();
        Date expectedReturnDate = new Date();

        LoanComponent loanComponent = new LoanComponentBuilder()
                .withBorrower(borrower)
                .withAuthorizer(authorizer)
                .withComponent(component)
                .withLoanDate(loanDate)
                .withExpectedReturnDate(expectedReturnDate)
                .withStatus(LoanStatus.APPROVED)
                .withQuantitys(10)
                .build();

        assertNotNull(loanComponent.getId(), "LoanComponent ID should not be null");
        assertEquals(borrower, loanComponent.getBorrower(), "Borrower should match");
        assertEquals(authorizer, loanComponent.getAuthorizer(), "Authorizer should match");
        assertEquals(component, loanComponent.getComponent(), "Component should match");
        assertEquals(loanDate, loanComponent.getLoanDate(), "Loan date should match");
        assertEquals(expectedReturnDate, loanComponent.getExpectedReturnDate(), "Expected return date should match");
        assertEquals(LoanStatus.APPROVED, loanComponent.getStatus(), "Status should match");
        assertEquals(10, loanComponent.getQuantity(), "Quantity should match");
    }

    @Test
    void testDefaultLoanComponentConstructor() {
        LoanComponent loanComponent = new LoanComponent();
        assertNull(loanComponent.getBorrower(), "Default borrower should be null");
        assertNull(loanComponent.getComponent(), "Default component should be null");
        assertEquals(0, loanComponent.getQuantity(), "Default quantity should be zero");
    }

    // LoanHistory Tests
    @Test
    void testLoanHistoryDefaultConstructor() {
        LoanHistory loanHistory = new LoanHistory();
        assertNotNull(loanHistory.getLoanId(), "Default loan ID should not be null");
        assertNull(loanHistory.getBorrowerName(), "Default borrower name should be null");
        assertNull(loanHistory.getComponentName(), "Default component name should be null");
    }

    // ComponentWithLoanDetailsDTO Tests
    @Test
    void testComponentWithLoanDetailsDTO() {
        UUID id = UUID.randomUUID();
        String name = "Resistor";
        String serialNumber = "RS-12345";
        String description = "A standard resistor";
        int totalQuantity = 100;

        ComponentWithLoanDetailsDTO dto = new ComponentWithLoanDetailsDTO(id, name, serialNumber, description, totalQuantity);

        assertEquals(id, dto.getId(), "ID should match");
        assertEquals(name, dto.getName(), "Name should match");
        assertEquals(serialNumber, dto.getSerialNumber(), "Serial number should match");
        assertEquals(description, dto.getDescription(), "Description should match");
        assertEquals(totalQuantity, dto.getTotalQuantity(), "Total quantity should match");
    }

    // LoanAuthorizationDTO Tests
    @Test
    void testLoanAuthorizationDTO() {
        String loanId = "LOAN123";
        LoanStatus status = LoanStatus.APPROVED;
        int authorizedQuantity = 5;
        String authorizerEmail = "authorizer@example.com";

        LoanAuthorizationDTO dto = new LoanAuthorizationDTO();
        dto.setLoanId(loanId);
        dto.setStatus(status);
        dto.setAuthorizedQuantity(authorizedQuantity);
        dto.setAuthorizerEmail(authorizerEmail);

        assertEquals(loanId, dto.getLoanId(), "Loan ID should match");
        assertEquals(status, dto.getStatus(), "Status should match");
        assertEquals(authorizedQuantity, dto.getAuthorizedQuantity(), "Authorized quantity should match");
        assertEquals(authorizerEmail, dto.getAuthorizerEmail(), "Authorizer email should match");
    }

    // LoanComponentDTO Tests
    @Test
    void testLoanComponentDTO() {
        UUID id = UUID.randomUUID();
        String loanId = "LOAN123";
        UUID borrowerId = UUID.randomUUID();
        String borrowerEmail = "borrower@example.com";
        UUID authorizerId = UUID.randomUUID();
        String authorizerEmail = "authorizer@example.com";
        UUID componentId = UUID.randomUUID();
        String componentName = "Resistor";
        Date loanDate = new Date();
        Date expectedReturnDate = new Date();
        LoanStatus status = LoanStatus.RETURNED;
        int quantity = 10;

        LoanComponentDTO dto = new LoanComponentDTO(id, loanId, borrowerId, borrowerEmail, authorizerId, authorizerEmail,
                componentId, componentName, loanDate, expectedReturnDate, null, status, quantity);

        assertEquals(id, dto.getId(), "ID should match");
        assertEquals(loanId, dto.getLoanId(), "Loan ID should match");
        assertEquals(borrowerId, dto.getBorrowerId(), "Borrower ID should match");
        assertEquals(borrowerEmail, dto.getBorrowerEmail(), "Borrower email should match");
        assertEquals(authorizerId, dto.getAuthorizerId(), "Authorizer ID should match");
        assertEquals(authorizerEmail, dto.getAuthorizerEmail(), "Authorizer email should match");
        assertEquals(componentId, dto.getComponentId(), "Component ID should match");
        assertEquals(componentName, dto.getComponentName(), "Component name should match");
        assertEquals(loanDate, dto.getLoanDate(), "Loan date should match");
        assertEquals(expectedReturnDate, dto.getExpectedReturnDate(), "Expected return date should match");
        assertEquals(status, dto.getStatus(), "Status should match");
        assertEquals(quantity, dto.getQuantity(), "Quantity should match");
    }

    // LoanReturnDTO Tests
    @Test
    void testLoanReturnDTO() {
        UUID loanId = UUID.randomUUID();
        int returnedQuantity = 5;
        String borrowerEmail = "borrower@example.com";

        LoanReturnDTO dto = new LoanReturnDTO();
        dto.setLoanId(loanId);
        dto.setReturnedQuantity(returnedQuantity);
        dto.setBorrowerEmail(borrowerEmail);

        assertEquals(loanId, dto.getLoanId(), "Loan ID should match");
        assertEquals(returnedQuantity, dto.getReturnedQuantity(), "Returned quantity should match");
        assertEquals(borrowerEmail, dto.getBorrowerEmail(), "Borrower email should match");
    }


}
