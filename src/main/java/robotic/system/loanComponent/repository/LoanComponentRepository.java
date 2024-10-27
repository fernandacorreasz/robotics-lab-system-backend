package robotic.system.loanComponent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    
}
