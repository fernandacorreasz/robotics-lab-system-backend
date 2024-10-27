package robotic.system.loanComponent.app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import robotic.system.inventory.domain.Component;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.repository.UserRepository;

@Service
public class LoanAuthorizationService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private UserRepository userRepository;

    public LoanComponent authorizeLoan(UUID loanId, String status, int authorizedQuantity, String authorizerEmail) {
        Optional<LoanComponent> loanOpt = loanComponentRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        LoanComponent loan = loanOpt.get();
        loan.setStatus(status);
        loan.setAuthorizer(userRepository.findByEmail(authorizerEmail));

        // Atualizar o estoque
        Component component = loan.getComponent();
        if (status.equals("Aprovado")) {
            component.setQuantity(component.getQuantity() - authorizedQuantity);
            componentRepository.save(component);
        }

        return loanComponentRepository.save(loan);
    }
}
