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
public class LoanReturnService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public LoanComponent registerReturn(UUID loanId, int returnedQuantity, String borrowerEmail) {
        Optional<LoanComponent> loanOpt = loanComponentRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        LoanComponent loan = loanOpt.get();

        // Verificar se o usuário que está tentando realizar a devolução é o mesmo que pegou o empréstimo
        Users borrower = userRepository.findOptionalByEmail(borrowerEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + borrowerEmail));

        if (!loan.getBorrower().getId().equals(borrower.getId())) {
            throw new RuntimeException("Apenas o usuário que realizou o empréstimo pode devolvê-lo.");
        }

        // Verificar se a quantidade devolvida é válida
        if (returnedQuantity > loan.getQuantity()) {
            throw new IllegalArgumentException("A quantidade devolvida não pode ser maior que a quantidade emprestada.");
        }

        // Atualizar o status e a data de devolução
        loan.setActualReturnDate(new Date());
        loan.setStatus(LoanStatus.RETURNED);

        // Registrar a devolução no banco de dados
        return loanComponentRepository.save(loan);
    }
}
