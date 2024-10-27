package robotic.system.loanComponent.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import robotic.system.loanComponent.app.service.*;
import robotic.system.loanComponent.domain.model.LoanComponent;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanComponentController {

    @Autowired
    private LoanRequestService loanRequestService;

    @Autowired
    private LoanAuthorizationService loanAuthorizationService;

    @Autowired
    private LoanPickupService loanPickupService;

    @Autowired
    private LoanReturnService loanReturnService;

    @Autowired
    private LoanReturnAuthorizationService loanReturnAuthorizationService;

    // 1. Solicitação de Empréstimo
    @PostMapping("/request")
    public ResponseEntity<LoanComponent> requestLoan(
            @RequestParam String componentName,
            @RequestParam int quantity,
            @RequestParam Date expectedReturnDate,
            @RequestParam String borrowerEmail) {

        LoanComponent loan = loanRequestService.requestLoan(componentName, quantity, expectedReturnDate, borrowerEmail);
        return ResponseEntity.ok(loan);
    }

    // 2. Autorização do Empréstimo
    @PostMapping("/authorize")
    public ResponseEntity<LoanComponent> authorizeLoan(
            @RequestParam UUID loanId,
            @RequestParam String status,
            @RequestParam int authorizedQuantity,
            @RequestParam String authorizerEmail) {

        LoanComponent loan = loanAuthorizationService.authorizeLoan(loanId, status, authorizedQuantity, authorizerEmail);
        return ResponseEntity.ok(loan);
    }

    // 3. Registrar Retirada
    @PostMapping("/pickup")
    public ResponseEntity<LoanComponent> registerPickup(@RequestParam UUID loanId) {
        LoanComponent loan = loanPickupService.registerPickup(loanId);
        return ResponseEntity.ok(loan);
    }

    // 4. Registrar Devolução
    @PostMapping("/return")
    public ResponseEntity<LoanComponent> registerReturn(
            @RequestParam UUID loanId,
            @RequestParam int returnedQuantity) {

        LoanComponent loan = loanReturnService.registerReturn(loanId, returnedQuantity);
        return ResponseEntity.ok(loan);
    }

    // 5. Autorizar Devolução
    @PostMapping("/authorize-return")
    public ResponseEntity<LoanComponent> authorizeReturn(
            @RequestParam UUID loanId,
            @RequestParam String status,
            @RequestParam String authorizerEmail) {

        LoanComponent loan = loanReturnAuthorizationService.authorizeReturn(loanId, status, authorizerEmail);
        return ResponseEntity.ok(loan);
    }
}
