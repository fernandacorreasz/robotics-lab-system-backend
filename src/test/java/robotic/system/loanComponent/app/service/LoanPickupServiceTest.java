package robotic.system.loanComponent.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanPickupServiceTest {

    @Mock
    private LoanComponentRepository loanComponentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanPickupService loanPickupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterPickup_LoanNotFound() {
        UUID loanId = UUID.randomUUID();
        String userEmail = "authorizer@example.com";

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                loanPickupService.registerPickup(loanId, userEmail));

        assertEquals("Empréstimo não encontrado.", exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verifyNoInteractions(userRepository);
    }

    @Test
    void testRegisterPickup_UserNotFound() {
        UUID loanId = UUID.randomUUID();
        String userEmail = "authorizer@example.com";

        LoanComponent loanMock = mock(LoanComponent.class);
        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(loanMock));
        when(userRepository.findOptionalByEmail(userEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                loanPickupService.registerPickup(loanId, userEmail));

        assertEquals("Usuário não encontrado: " + userEmail, exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verify(userRepository).findOptionalByEmail(userEmail);
    }

    @Test
    void testRegisterPickup_UnauthorizedUser() {
        UUID loanId = UUID.randomUUID();
        String userEmail = "unauthorized@example.com";

        LoanComponent loanMock = mock(LoanComponent.class);
        Users authorizerMock = mock(Users.class);
        Users unauthorizedUserMock = mock(Users.class);

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(loanMock));
        when(userRepository.findOptionalByEmail(userEmail)).thenReturn(Optional.of(unauthorizedUserMock));
        when(loanMock.getAuthorizer()).thenReturn(authorizerMock);
        when(authorizerMock.getId()).thenReturn(UUID.randomUUID());
        when(unauthorizedUserMock.getId()).thenReturn(UUID.randomUUID());

        Exception exception = assertThrows(RuntimeException.class, () ->
                loanPickupService.registerPickup(loanId, userEmail));

        assertEquals("Apenas o usuário que autorizou o empréstimo pode realizar a retirada.", exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verify(userRepository).findOptionalByEmail(userEmail);
    }

    @Test
    void testRegisterPickup_InvalidLoanStatus() {
        UUID loanId = UUID.randomUUID();
        String userEmail = "authorizer@example.com";

        LoanComponent loanMock = mock(LoanComponent.class);
        Users authorizerMock = mock(Users.class);

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(loanMock));
        when(userRepository.findOptionalByEmail(userEmail)).thenReturn(Optional.of(authorizerMock));
        when(loanMock.getAuthorizer()).thenReturn(authorizerMock);
        when(authorizerMock.getId()).thenReturn(UUID.randomUUID());
        when(loanMock.getStatus()).thenReturn(LoanStatus.PENDING_AUTHORIZATION);

        Exception exception = assertThrows(RuntimeException.class, () ->
                loanPickupService.registerPickup(loanId, userEmail));

        assertEquals("O empréstimo deve estar aprovado para realizar a retirada.", exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verify(userRepository).findOptionalByEmail(userEmail);
    }
}
