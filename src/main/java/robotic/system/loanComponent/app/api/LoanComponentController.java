package robotic.system.loanComponent.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import robotic.system.loanComponent.app.service.*;
import robotic.system.loanComponent.domain.dto.LoanAuthorizationDTO;
import robotic.system.loanComponent.domain.dto.LoanRequestDTO;
import robotic.system.loanComponent.domain.dto.LoanReturnDTO;
import robotic.system.loanComponent.domain.model.LoanComponent;

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
    private LoanOverdueCheckService loanOverdueCheckService;

    // 1. Solicitação de Empréstimo
    @PostMapping("/request")
    public ResponseEntity<?> requestLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        try {
            LoanComponent loan = loanRequestService.requestLoan(
                    loanRequestDTO.getComponentName(),
                    loanRequestDTO.getQuantity(),
                    loanRequestDTO.getExpectedReturnDate(),
                    loanRequestDTO.getBorrowerEmail());
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Autorização do Empréstimo
    @PostMapping("/authorize")
    public ResponseEntity<LoanComponent> authorizeLoan(@RequestBody LoanAuthorizationDTO loanAuthorizationDTO) {
        LoanComponent loan = loanAuthorizationService.authorizeLoan(
                loanAuthorizationDTO.getLoanId(),
                loanAuthorizationDTO.getStatus(),
                loanAuthorizationDTO.getAuthorizedQuantity(),
                loanAuthorizationDTO.getAuthorizerEmail());
        return ResponseEntity.ok(loan);
    }

    // 3. Registrar Retirada
    @PostMapping("/pickup")
    public ResponseEntity<LoanComponent> registerPickup(@RequestParam UUID loanId, @RequestParam String userEmail) {
        LoanComponent loan = loanPickupService.registerPickup(loanId, userEmail);
        return ResponseEntity.ok(loan);
    }

    // 4. Registrar Devolução
    @PostMapping("/return")
    public ResponseEntity<LoanComponent> registerReturn(@RequestBody LoanReturnDTO loanReturnDTO) {
        LoanComponent loan = loanReturnService.registerReturn(
                loanReturnDTO.getLoanId(),
                loanReturnDTO.getReturnedQuantity(),
                loanReturnDTO.getBorrowerEmail());
        return ResponseEntity.ok(loan);
    }

    // Endpoint para verificar empréstimos em atraso e enviar notificações
    @GetMapping("/check-overdue")
    public ResponseEntity<List<LoanComponent>> checkOverdueLoans(@RequestParam String email) {
        List<LoanComponent> overdueLoans = loanOverdueCheckService.checkOverdueLoansForEmail(email);
        return ResponseEntity.ok(overdueLoans);
    }
}
