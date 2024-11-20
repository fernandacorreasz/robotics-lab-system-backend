package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import robotic.system.inventory.domain.dto.ComponentWithLoanDetailsDTO;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.loanComponent.domain.dto.LoanComponentDTO;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanComponentService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private BulkDeleteService bulkDeleteService;

    @Autowired
    private ComponentRepository componentRepository;

    public Page<LoanComponentDTO> listAllLoanComponentsIncludingNulls(Pageable pageable) {
        return loanComponentRepository.findAllLoanComponentsWithAllDetails(pageable);
    }

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

    //LISTA DE COMPONENTES CAULCO DE QUANTIDADE
    public List<ComponentWithLoanDetailsDTO> getComponentsWithLoanDetails() {
        // Busca os dados brutos do repositório
        List<Object[]> rawData = componentRepository.findAllComponentsWithLoanStatusGrouped();

        // Map para armazenar os resultados agrupados por componente
        Map<UUID, ComponentWithLoanDetailsDTO> componentMap = new HashMap<>();

        for (Object[] row : rawData) {
            UUID componentId = (UUID) row[0];
            String name = (String) row[1];
            String serialNumber = (String) row[2];
            String description = (String) row[3];
            int totalQuantity = ((Number) row[4]).intValue();
            String statusValue = (String) row[5];
            int quantity = ((Number) row[6]).intValue();

            LoanStatus status;
            try {
                status = LoanStatus.valueOf(statusValue);
            } catch (IllegalArgumentException e) {
                // Ignorar ou logar status inválidos
                continue;
            }

            // Busca ou cria o DTO do componente
            ComponentWithLoanDetailsDTO dto = componentMap.getOrDefault(componentId,
                    new ComponentWithLoanDetailsDTO(componentId, name, serialNumber, description, totalQuantity));

            // Atualiza os valores baseados no status
            switch (status) {
                case PENDING_AUTHORIZATION -> dto.setRequestedQuantity(dto.getRequestedQuantity() + quantity);
                case APPROVED -> dto.setAuthorizedQuantity(dto.getAuthorizedQuantity() + quantity);
                case IN_PROGRESS -> dto.setBorrowedQuantity(dto.getBorrowedQuantity() + quantity);
                default -> {
                    // Outros status são ignorados no cálculo atual
                }
            }

            // Recalcula a quantidade disponível
            dto.setAvailableQuantity(dto.getTotalQuantity() - dto.getRequestedQuantity()
                    - dto.getAuthorizedQuantity() - dto.getBorrowedQuantity());

            // Atualiza o Map
            componentMap.put(componentId, dto);
        }

        // Retorna a lista dos valores calculados
        return new ArrayList<>(componentMap.values());
    }
}