package robotic.system.loanComponent.app.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.inventory.domain.Component;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanAuthorizationServiceTest {

    @Mock
    private LoanComponentRepository loanComponentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanAuthorizationService loanAuthorizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthorizeLoan_Success() {
        String loanId = "1234";
        String authorizerEmail = "authorizer@example.com";
        int authorizedQuantity = 5;

        LoanComponent mockLoan = mock(LoanComponent.class);
        Users mockAuthorizer = mock(Users.class);
        Component mockComponent = mock(Component.class);

        when(loanComponentRepository.findByLoanId(loanId)).thenReturn(Optional.of(mockLoan));
        when(userRepository.findOptionalByEmail(authorizerEmail)).thenReturn(Optional.of(mockAuthorizer));
        when(mockLoan.getComponent()).thenReturn(mockComponent);
        when(mockComponent.getId()).thenReturn(UUID.randomUUID());
        when(mockComponent.getQuantity()).thenReturn(10);
        when(loanComponentRepository.sumLoanedQuantitiesByComponentId(any(UUID.class), eq(LoanStatus.RETURNED))).thenReturn(3);
        when(loanComponentRepository.save(mockLoan)).thenReturn(mockLoan);

        LoanComponent result = loanAuthorizationService.authorizeLoan(
                loanId, LoanStatus.APPROVED, authorizedQuantity, authorizerEmail);

        assertNotNull(result);
        verify(mockLoan).setQuantity(authorizedQuantity);
        verify(mockLoan).setStatus(LoanStatus.APPROVED);
        verify(mockLoan).setAuthorizer(mockAuthorizer);
        verify(loanComponentRepository).save(mockLoan);
    }

    @Test
    void testAuthorizeLoan_LoanNotFound() {
        String loanId = "1234";

        when(loanComponentRepository.findByLoanId(loanId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                loanAuthorizationService.authorizeLoan(loanId, LoanStatus.APPROVED, 5, "authorizer@example.com"));

        assertEquals("Empréstimo não encontrado: 1234", exception.getMessage());
        verifyNoInteractions(userRepository);
    }

    @Test
    void testAuthorizeLoan_AuthorizerNotFound() {
        String loanId = "1234";
        String authorizerEmail = "authorizer@example.com";

        LoanComponent mockLoan = mock(LoanComponent.class);

        when(loanComponentRepository.findByLoanId(loanId)).thenReturn(Optional.of(mockLoan));
        when(userRepository.findOptionalByEmail(authorizerEmail)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                loanAuthorizationService.authorizeLoan(loanId, LoanStatus.APPROVED, 5, authorizerEmail));

        assertEquals("Usuário autorizador não encontrado: authorizer@example.com", exception.getMessage());
    }

    @Test
    void testAuthorizeLoan_InsufficientComponents() {
        String loanId = "1234";
        String authorizerEmail = "authorizer@example.com";
        int authorizedQuantity = 8;

        LoanComponent mockLoan = mock(LoanComponent.class);
        Users mockAuthorizer = mock(Users.class);
        Component mockComponent = mock(Component.class);

        when(loanComponentRepository.findByLoanId(loanId)).thenReturn(Optional.of(mockLoan));
        when(userRepository.findOptionalByEmail(authorizerEmail)).thenReturn(Optional.of(mockAuthorizer));
        when(mockLoan.getComponent()).thenReturn(mockComponent);
        when(mockComponent.getId()).thenReturn(UUID.randomUUID());
        when(mockComponent.getQuantity()).thenReturn(10);
        when(loanComponentRepository.sumLoanedQuantitiesByComponentId(any(UUID.class), eq(LoanStatus.RETURNED))).thenReturn(6);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                loanAuthorizationService.authorizeLoan(loanId, LoanStatus.APPROVED, authorizedQuantity, authorizerEmail));

        assertEquals("Não há componentes suficientes para autorizar o empréstimo. Disponíveis: 4", exception.getMessage());
    }


}
