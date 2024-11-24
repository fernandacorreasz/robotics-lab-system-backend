package robotic.system.loanComponent.app.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoanRejectionService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public LoanComponent rejectLoan(UUID loanId, String authorizerEmail) {

        Optional<LoanComponent> loanOpt = loanComponentRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Empréstimo não encontrado para o ID: " + loanId);
        }

        LoanComponent loan = loanOpt.get();

        if (loan.getStatus() != LoanStatus.PENDING_AUTHORIZATION) {
            throw new IllegalStateException("Apenas empréstimos pendentes podem ser recusados.");
        }

        loan.setStatus(LoanStatus.REJECTED);

        return loanComponentRepository.save(loan);
    }
}
