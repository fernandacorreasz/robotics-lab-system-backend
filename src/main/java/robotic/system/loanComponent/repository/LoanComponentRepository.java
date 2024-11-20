package robotic.system.loanComponent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import robotic.system.loanComponent.domain.dto.LoanComponentDTO;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanComponentRepository extends JpaRepository<LoanComponent, UUID> {

    @Query("SELECT SUM(lc.quantity) FROM LoanComponent lc WHERE lc.component.id = :componentId AND lc.status != :status")
    Integer sumLoanedQuantitiesByComponentId(UUID componentId, LoanStatus status);

    Optional<LoanComponent> findByLoanId(String loanId);

    @Query("SELECT l FROM LoanComponent l WHERE l.expectedReturnDate < :currentDate AND l.status = :status")
    List<LoanComponent> findOverdueLoans(Date currentDate, LoanStatus status);

    @Query("SELECT new robotic.system.loanComponent.domain.dto.LoanComponentDTO(l.id, l.loanId, "
            + "l.borrower.id, l.borrower.email, l.authorizer.id, l.authorizer.email, "
            + "l.component.id, l.component.name, l.loanDate, l.expectedReturnDate, "
            + "l.actualReturnDate, l.status, l.quantity) "
            + "FROM LoanComponent l")
    Page<LoanComponentDTO> findAllLoanComponentsWithDetails(Pageable pageable);

    @Query("SELECT new robotic.system.loanComponent.domain.dto.LoanComponentDTO(l.id, l.loanId, "
            + "l.borrower.id, l.borrower.email, l.authorizer.id, l.authorizer.email, "
            + "l.component.id, l.component.name, l.loanDate, l.expectedReturnDate, "
            + "l.actualReturnDate, l.status, l.quantity) "
            + "FROM LoanComponent l WHERE l.id = :loanId")
    Optional<LoanComponentDTO> findLoanById(UUID loanId);

    @Query("SELECT new robotic.system.loanComponent.domain.dto.LoanComponentDTO(l.id, l.loanId, "
    + "l.borrower.id, l.borrower.email, l.authorizer.id, l.authorizer.email, "
    + "l.component.id, l.component.name, l.loanDate, l.expectedReturnDate, "
    + "l.actualReturnDate, l.status, l.quantity) "
    + "FROM LoanComponent l WHERE (:loanId IS NULL OR l.loanId = :loanId) "
    + "AND (:borrowerEmail IS NULL OR l.borrower.email = :borrowerEmail) "
    + "AND (:authorizerEmail IS NULL OR l.authorizer.email = :authorizerEmail) "
    + "AND (:componentName IS NULL OR l.component.name = :componentName)")
Page<LoanComponentDTO> findAllLoanComponentsWithFilters(
    String loanId, String borrowerEmail, String authorizerEmail, String componentName, Pageable pageable
);

    @Query("SELECT new robotic.system.loanComponent.domain.dto.LoanComponentDTO(l.id, l.loanId, "
            + "l.borrower.id, l.borrower.email, l.authorizer.id, l.authorizer.email, "
            + "l.component.id, l.component.name, l.loanDate, l.expectedReturnDate, "
            + "l.actualReturnDate, l.status, l.quantity) "
            + "FROM LoanComponent l "
            + "LEFT JOIN l.borrower b "
            + "LEFT JOIN l.authorizer a "
            + "LEFT JOIN l.component c")
    Page<LoanComponentDTO> findAllLoanComponentsWithAllDetails(Pageable pageable);

}
