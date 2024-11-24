package robotic.system.loanComponent.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.loanComponent.domain.en.LoanStatus;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.loanComponent.repository.LoanComponentRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanRejectionServiceTest {

    @Mock
    private LoanComponentRepository loanComponentRepository;

    @InjectMocks
    private LoanRejectionService loanRejectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRejectLoan_Success() {
        UUID loanId = UUID.randomUUID();
        String authorizerEmail = "authorizer@example.com";

        LoanComponent loanMock = mock(LoanComponent.class);

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(loanMock));
        when(loanMock.getStatus()).thenReturn(LoanStatus.PENDING_AUTHORIZATION);
        when(loanComponentRepository.save(loanMock)).thenReturn(loanMock);

        LoanComponent result = loanRejectionService.rejectLoan(loanId, authorizerEmail);

        assertNotNull(result, "O empréstimo não deve ser nulo.");
        verify(loanMock).setStatus(LoanStatus.REJECTED);
        verify(loanComponentRepository).save(loanMock);
    }

    @Test
    void testRejectLoan_LoanNotFound() {
        UUID loanId = UUID.randomUUID();
        String authorizerEmail = "authorizer@example.com";

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                loanRejectionService.rejectLoan(loanId, authorizerEmail));

        assertEquals("Empréstimo não encontrado para o ID: " + loanId, exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verifyNoMoreInteractions(loanComponentRepository);
    }

    @Test
    void testRejectLoan_InvalidLoanStatus() {
        UUID loanId = UUID.randomUUID();
        String authorizerEmail = "authorizer@example.com";

        LoanComponent loanMock = mock(LoanComponent.class);

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(loanMock));
        when(loanMock.getStatus()).thenReturn(LoanStatus.APPROVED);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                loanRejectionService.rejectLoan(loanId, authorizerEmail));

        assertEquals("Apenas empréstimos pendentes podem ser recusados.", exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verify(loanMock, never()).setStatus(any());
        verifyNoMoreInteractions(loanComponentRepository);
    }
}
