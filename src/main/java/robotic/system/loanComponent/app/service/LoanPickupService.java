package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanPickupService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public LoanComponent registerPickup(UUID loanId, String userEmail) {
        Optional<LoanComponent> loanOpt = loanComponentRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        LoanComponent loan = loanOpt.get();

        // Buscar o usuário que está tentando realizar a retirada
        Users user = userRepository.findOptionalByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userEmail));

        // Verificar se o usuário que está tentando realizar a retirada é o autorizador
        if (!loan.getAuthorizer().getId().equals(user.getId())) {
            throw new RuntimeException("Apenas o usuário que autorizou o empréstimo pode realizar a retirada.");
        }

        // Verificar se o status está correto para permitir a retirada
        if (loan.getStatus() != LoanStatus.APPROVED) {
            throw new RuntimeException("O empréstimo deve estar aprovado para realizar a retirada.");
        }

        if (loan.getStatus() != LoanStatus.IN_PROGRESS) {
            throw new RuntimeException("O empréstimo já foi feito a retirada.");
        }


        // Atualizar o status do empréstimo para "Em andamento"
        loan.setStatus(LoanStatus.IN_PROGRESS);
        loan.setLoanDate(new Date()); // Definir a data de retirada

        // Registrar a atualização no banco de dados
        return loanComponentRepository.save(loan);
    }
}
