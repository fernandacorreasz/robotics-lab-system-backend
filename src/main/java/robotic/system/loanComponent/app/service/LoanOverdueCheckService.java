package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.notification.app.service.NotificationService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanOverdueCheckService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private NotificationService notificationService;

    // Verificar e retornar os empréstimos em atraso para o e-mail especificado
    public List<LoanComponent> checkOverdueLoansForEmail(String email) {
        // Busca empréstimos em atraso com status "APPROVED"
        List<LoanComponent> overdueLoans = loanComponentRepository.findOverdueLoans(new Date(), LoanStatus.APPROVED);

        // Filtrar os empréstimos que estão associados ao e-mail (tanto do solicitante quanto do autorizador)
        List<LoanComponent> filteredLoans = overdueLoans.stream()
            .filter(loan -> loan.getBorrower().getEmail().equals(email) || 
                            (loan.getAuthorizer() != null && loan.getAuthorizer().getEmail().equals(email)))
            .collect(Collectors.toList());

        // Notificar o solicitante e o autorizador sobre o atraso, apenas se estiverem relacionados ao e-mail
        for (LoanComponent loan : filteredLoans) {
            notificationService.notifyOverdueLoan(loan);
        }

        return filteredLoans;
    }
}
