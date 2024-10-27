package robotic.system.loanComponent.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.UUID;

@Service
public class LoanAuthorizationService {

    @Autowired
    private LoanComponentRepository loanComponentRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public LoanComponent authorizeLoan(String loanId, LoanStatus status, int authorizedQuantity, String authorizerEmail) {
        // Buscar o empréstimo pelo loanId
        LoanComponent loan = loanComponentRepository.findByLoanId(loanId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado: " + loanId));

        // Buscar o autorizador pelo e-mail
        Users authorizer = userRepository.findOptionalByEmail(authorizerEmail)
                .orElseThrow(() -> new RuntimeException("Usuário autorizador não encontrado: " + authorizerEmail));

        // Buscar o componente relacionado ao empréstimo
        Component component = loan.getComponent();

        // Calcular a quantidade de componentes já emprestados
        Integer totalLoaned = loanComponentRepository.sumLoanedQuantitiesByComponentId(component.getId(), LoanStatus.RETURNED);
        if (totalLoaned == null) {
            totalLoaned = 0;
        }

        // Calcular quantos componentes ainda estão disponíveis
        int availableQuantity = component.getQuantity() - totalLoaned;

        // Verificar a disponibilidade antes de aprovar o empréstimo
        if (status == LoanStatus.APPROVED) {
            if (authorizedQuantity > availableQuantity) {
                throw new RuntimeException("Não há componentes suficientes para autorizar o empréstimo. Disponíveis: " + availableQuantity);
            }
            loan.setQuantity(authorizedQuantity);  // Atualiza a quantidade autorizada
        }

        // Atualizar o status do empréstimo e o autorizador
        loan.setStatus(status);
        loan.setAuthorizer(authorizer);

        // Registrar as alterações no banco de dados
        return loanComponentRepository.save(loan);
    }
}
