package robotic.system.loanComponent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import robotic.system.loanComponent.domain.model.LoanComponent;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface LoanComponentRepository extends JpaRepository<LoanComponent, UUID> {

    @Query("SELECT l FROM LoanComponent l WHERE l.expectedReturnDate < :currentDate AND l.status = 'Em andamento'")
    List<LoanComponent> findOverdueLoans(Date currentDate);
}
