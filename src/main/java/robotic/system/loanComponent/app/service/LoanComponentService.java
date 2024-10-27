package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import robotic.system.loanComponent.domain.dto.LoanComponentDTO;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanComponentService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private BulkDeleteService bulkDeleteService;

    public Page<LoanComponentDTO> listAllLoanComponents(Pageable pageable) {
        return loanComponentRepository.findAllLoanComponentsWithDetails(pageable);
    }

    public Optional<LoanComponentDTO> getLoanById(UUID loanId) {
        return loanComponentRepository.findLoanById(loanId);
    }

    private static final Set<String> VALID_COLUMNS = Set.of(
            "loanId", "borrower.email", "authorizer.email", "component.name", "loanDate",
            "expectedReturnDate", "actualReturnDate", "status", "quantity");

    public Page<LoanComponentDTO> filterLoanComponents(List<FilterRequest> filters, Pageable pageable) {

        List<FilterRequest> validFilters = filters.stream()
                .filter(filter -> VALID_COLUMNS.contains(filter.getColumn()))
                .collect(Collectors.toList());

        if (validFilters.isEmpty()) {
            return Page.empty(pageable);
        }

        String loanId = null;
        String borrowerEmail = null;
        String authorizerEmail = null;
        String componentName = null;

        for (FilterRequest filter : validFilters) {
            String column = filter.getColumn();
            String value = filter.getValue();

            switch (column) {
                case "loanId":
                    loanId = value;
                    break;
                case "borrower.email":
                    borrowerEmail = value;
                    break;
                case "authorizer.email":
                    authorizerEmail = value;
                    break;
                case "component.name":
                    componentName = value;
                    break;
                default:
                    throw new IllegalArgumentException("Filtro inválido: " + column);
            }
        }

        return loanComponentRepository.findAllLoanComponentsWithFilters(
                loanId, borrowerEmail, authorizerEmail, componentName, pageable);
    }

    @Transactional
    public BulkDeleteService.BulkDeleteResult deleteLoanComponentsByIds(List<String> loanIds) {
        return bulkDeleteService.bulkDeleteByField(
                loanIds,
                this::findLoanComponentById,
                this::removeAndDeleteLoanComponent);
    }

    private LoanComponent findLoanComponentById(String loanId) {
        try {
            UUID id = UUID.fromString(loanId);
            return loanComponentRepository.findById(id).orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Boolean removeAndDeleteLoanComponent(LoanComponent loanComponent) {
        try {
            loanComponentRepository.delete(loanComponent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}