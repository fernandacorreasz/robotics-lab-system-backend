package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.notification.app.service.NotificationService;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LoanRequestService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public LoanComponent requestLoan(String componentName, int quantity, Date expectedReturnDate, String borrowerEmail) {
        // Buscar o componente pelo nome
        Component component = componentRepository.findByName(componentName)
                .orElseThrow(() -> new RuntimeException("Componente não encontrado: " + componentName));

        // Calcular a quantidade de componentes já emprestados
        Integer totalLoaned = loanComponentRepository.sumLoanedQuantitiesByComponentId(component.getId(), LoanStatus.RETURNED);
        
        if (totalLoaned == null) {
            totalLoaned = 0;
        }

        // Calcular quantos componentes ainda estão disponíveis
        int availableQuantity = component.getQuantity() - totalLoaned;

        // Verificar a disponibilidade
        if (availableQuantity <= 0) {
            throw new RuntimeException("Não há disponibilidade para empréstimo.");
        } else if (quantity > availableQuantity) {
            throw new RuntimeException("No momento, apenas " + availableQuantity + " componentes estão disponíveis para empréstimo.");
        }

        // Buscar o usuário que está solicitando o empréstimo
        Users borrower = userRepository.findOptionalByEmail(borrowerEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + borrowerEmail));

        // Criar a entidade de empréstimo (LoanComponent)
        LoanComponent loan = new LoanComponent();
        loan.setLoanId(UUID.randomUUID().toString());
        loan.setBorrower(borrower);
        loan.setComponent(component);
        loan.setLoanDate(new Date()); // Data de solicitação do empréstimo
        loan.setExpectedReturnDate(expectedReturnDate);
        loan.setQuantity(quantity);
        loan.setStatus(LoanStatus.PENDING_AUTHORIZATION);  // Setting initial status as PENDING_AUTHORIZATION

        // Registrar o empréstimo no banco de dados
        return loanComponentRepository.save(loan);
    }

    public void checkAndNotifyOverdueLoans() {
        List<LoanComponent> overdueLoans = loanComponentRepository.findOverdueLoans(new Date(), LoanStatus.OVERDUE);
        for (LoanComponent loan : overdueLoans) {
            notificationService.notifyOverdueLoan(loan);
        }
    }
}
