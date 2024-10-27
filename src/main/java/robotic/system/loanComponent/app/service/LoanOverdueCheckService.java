package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.notification.app.service.NotificationService;

import java.util.Date;
import java.util.List;

@Service
public class LoanOverdueCheckService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private NotificationService notificationService;

    public void checkOverdueLoans() {
        List<LoanComponent> overdueLoans = loanComponentRepository.findOverdueLoans(new Date());

        for (LoanComponent loan : overdueLoans) {
            // Notificar sobre o empréstimo atrasado
            notificationService.notifyOverdueLoan(loan);
        }
    }
}
