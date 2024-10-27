package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoanReturnAuthorizationService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private UserRepository userRepository;

    public LoanComponent authorizeReturn(UUID loanId, String status, String authorizerEmail) {
        Optional<LoanComponent> loanOpt = loanComponentRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        LoanComponent loan = loanOpt.get();
        loan.setStatus(status);
        loan.setReturnAuthorizer(userRepository.findByEmail(authorizerEmail));

        return loanComponentRepository.save(loan);
    }
}
