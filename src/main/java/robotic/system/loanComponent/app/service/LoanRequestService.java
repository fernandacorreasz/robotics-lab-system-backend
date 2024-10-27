package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class LoanRequestService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public LoanComponent requestLoan(String componentName, int quantity, Date expectedReturnDate, String borrowerEmail) {
        // Buscar o componente pelo nome
        Component component = componentRepository.findByName(componentName)
                .orElseThrow(() -> new RuntimeException("Componente não encontrado: " + componentName));

        // Verificar se há estoque disponível
        if (component.getQuantity() < quantity) {
            throw new RuntimeException("Quantidade solicitada excede o estoque disponível.");
        }

        // Buscar o usuário que está solicitando o empréstimo (pelo e-mail)
        Users borrower = userRepository.findByEmail(borrowerEmail);

        // Criar a entidade de empréstimo (LoanComponent)
        LoanComponent loan = new LoanComponent();
        loan.setLoanId(UUID.randomUUID().toString());
        loan.setBorrower(borrower);
        loan.setComponent(component);
        loan.setLoanDate(new Date()); // Data de solicitação do empréstimo
        loan.setExpectedReturnDate(expectedReturnDate);
        loan.setStatus("Pendente de Autorização");

        // Registrar o empréstimo no banco de dados
        LoanComponent savedLoan = loanComponentRepository.save(loan);

        // (Opcional) Notificar o autorizador que há um novo pedido de empréstimo
        // notificationService.notifyAuthorizer(loan);

        return savedLoan;
    }
}
