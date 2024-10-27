package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanPickupService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    public LoanComponent registerPickup(UUID loanId) {
        Optional<LoanComponent> loanOpt = loanComponentRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        LoanComponent loan = loanOpt.get();
        loan.setStatus("Em andamento");
        loan.setLoanDate(new Date());

        return loanComponentRepository.save(loan);
    }
}
