package robotic.system.loanComponent.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import robotic.system.loanComponent.app.service.*;
import robotic.system.loanComponent.domain.dto.*;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
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

    @Autowired
    private LoanComponentService loanComponentService;

    @Autowired
    private LoanRejectionService loanRejectionService;

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

    @GetMapping("/all")
    public ResponseEntity<Page<LoanComponentDTO>> listAllLoans(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<LoanComponentDTO> loansPage = loanComponentService.listAllLoanComponents(PageRequest.of(page, size));
        return ResponseEntity.ok(loansPage);
    }

    @GetMapping("/all-inclusive")
    public ResponseEntity<Page<LoanComponentDTO>> listAllLoansIncludingNulls(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<LoanComponentDTO> loansPage = loanComponentService.listAllLoanComponentsIncludingNulls(PageRequest.of(page, size));
        return ResponseEntity.ok(loansPage);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanComponentDTO> getLoanById(@PathVariable UUID loanId) {
        Optional<LoanComponentDTO> loanOpt = loanComponentService.getLoanById(loanId);
        return loanOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<LoanComponentDTO>> filterLoanComponents(
            @RequestBody List<FilterRequest> filters,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<LoanComponentDTO> loanComponents = loanComponentService.filterLoanComponents(filters, PageRequest.of(page, size));
        return ResponseEntity.ok(loanComponents);
    }

    @DeleteMapping("/bulk-delete")
    public ResponseEntity<BulkDeleteService.BulkDeleteResult> deleteLoanComponents(@RequestBody List<String> loanIds) {
        BulkDeleteService.BulkDeleteResult result = loanComponentService.deleteLoanComponentsByIds(loanIds);

        if (result.getFailedItems().isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<List<ComponentWithLoanDetailsDTO>> getComponentsWithLoanDetails() {
        List<ComponentWithLoanDetailsDTO> components = loanComponentService.getComponentsWithLoanDetails();
        return ResponseEntity.ok(components);
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectLoan(@RequestBody LoanRejectionDTO loanRejectionDTO) {
        try {
            UUID loanId = loanRejectionDTO.getLoanId();
            String authorizerEmail = loanRejectionDTO.getAuthorizerEmail();

            LoanComponent loan = loanRejectionService.rejectLoan(loanId, authorizerEmail);

            return ResponseEntity.ok(loan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao processar a solicitação.");
        }
    }

}
