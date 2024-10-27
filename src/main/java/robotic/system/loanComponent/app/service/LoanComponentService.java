package robotic.system.loanComponent.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.inventory.domain.Component;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;
import robotic.system.inventory.repository.ComponentRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanComponentService {


    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private UserRepository userRepository;

    public LoanComponent requestLoan(String componentName, int quantity, Date expectedReturnDate, String borrowerEmail) {
        // Verificar disponibilidade do componente
        Optional<Component> componentOpt = componentRepository.findByName(componentName);
        if (componentOpt.isEmpty() || componentOpt.get().getQuantity() < quantity) {
            throw new IllegalArgumentException("Componente não disponível ou quantidade insuficiente.");
        }

        // Criar a solicitação de empréstimo
        LoanComponent loan = new LoanComponent();
        loan.setComponent(componentOpt.get());
        loan.setExpectedReturnDate(expectedReturnDate);
        loan.setLoanDate(new Date());
        loan.setBorrower(userRepository.findByEmail(borrowerEmail)); // Associa o usuário logado

        loan.setStatus("Pendente de Autorização");
        return loanComponentRepository.save(loan);
    }
}