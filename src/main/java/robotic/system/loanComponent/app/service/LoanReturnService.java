package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanReturnService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    public LoanComponent registerReturn(UUID loanId, int returnedQuantity) {
        Optional<LoanComponent> loanOpt = loanComponentRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        LoanComponent loan = loanOpt.get();
        loan.setActualReturnDate(new Date());
        loan.setStatus("Pendente de Autorização de Devolução");

        return loanComponentRepository.save(loan);
    }
}
