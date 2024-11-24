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

class LoanReturnServiceTest {

    @Mock
    private LoanComponentRepository loanComponentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanReturnService loanReturnService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterReturn_Success() {
        UUID loanId = UUID.randomUUID();
        int returnedQuantity = 5;
        String borrowerEmail = "borrower@example.com";

        LoanComponent mockLoan = mock(LoanComponent.class);
        Users mockBorrower = mock(Users.class);

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(mockLoan));
        when(userRepository.findOptionalByEmail(borrowerEmail)).thenReturn(Optional.of(mockBorrower));
        when(mockLoan.getBorrower()).thenReturn(mockBorrower);
        when(mockBorrower.getId()).thenReturn(UUID.randomUUID());
        when(mockLoan.getQuantity()).thenReturn(5);
        when(loanComponentRepository.save(mockLoan)).thenReturn(mockLoan);

        LoanComponent result = loanReturnService.registerReturn(loanId, returnedQuantity, borrowerEmail);

        assertNotNull(result);
        verify(loanComponentRepository).findById(loanId);
        verify(userRepository).findOptionalByEmail(borrowerEmail);
        verify(mockLoan).setActualReturnDate(any(Date.class));
        verify(mockLoan).setStatus(LoanStatus.RETURNED);
        verify(loanComponentRepository).save(mockLoan);
    }

    @Test
    void testRegisterReturn_LoanNotFound() {
        UUID loanId = UUID.randomUUID();
        int returnedQuantity = 5;
        String borrowerEmail = "borrower@example.com";

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                loanReturnService.registerReturn(loanId, returnedQuantity, borrowerEmail));

        assertEquals("Empréstimo não encontrado.", exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verifyNoInteractions(userRepository);
    }

    @Test
    void testRegisterReturn_UserNotFound() {
        UUID loanId = UUID.randomUUID();
        int returnedQuantity = 5;
        String borrowerEmail = "nonexistent@example.com";

        LoanComponent mockLoan = mock(LoanComponent.class);

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(mockLoan));
        when(userRepository.findOptionalByEmail(borrowerEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                loanReturnService.registerReturn(loanId, returnedQuantity, borrowerEmail));

        assertEquals("Usuário não encontrado: " + borrowerEmail, exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verify(userRepository).findOptionalByEmail(borrowerEmail);
    }

    @Test
    void testRegisterReturn_UnauthorizedUser() {
        UUID loanId = UUID.randomUUID();
        int returnedQuantity = 5;
        String borrowerEmail = "borrower@example.com";

        LoanComponent mockLoan = mock(LoanComponent.class);
        Users mockBorrower = mock(Users.class);
        Users mockDifferentUser = mock(Users.class);

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(mockLoan));
        when(userRepository.findOptionalByEmail(borrowerEmail)).thenReturn(Optional.of(mockDifferentUser));
        when(mockLoan.getBorrower()).thenReturn(mockBorrower);
        when(mockBorrower.getId()).thenReturn(UUID.randomUUID());
        when(mockDifferentUser.getId()).thenReturn(UUID.randomUUID());

        Exception exception = assertThrows(RuntimeException.class, () ->
                loanReturnService.registerReturn(loanId, returnedQuantity, borrowerEmail));

        assertEquals("Apenas o usuário que realizou o empréstimo pode devolvê-lo.", exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verify(userRepository).findOptionalByEmail(borrowerEmail);
    }

    @Test
    void testRegisterReturn_ExcessiveReturnQuantity() {
        UUID loanId = UUID.randomUUID();
        int returnedQuantity = 10;
        String borrowerEmail = "borrower@example.com";

        LoanComponent mockLoan = mock(LoanComponent.class);
        Users mockBorrower = mock(Users.class);

        when(loanComponentRepository.findById(loanId)).thenReturn(Optional.of(mockLoan));
        when(userRepository.findOptionalByEmail(borrowerEmail)).thenReturn(Optional.of(mockBorrower));
        when(mockLoan.getBorrower()).thenReturn(mockBorrower);
        when(mockBorrower.getId()).thenReturn(UUID.randomUUID());
        when(mockLoan.getQuantity()).thenReturn(5);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                loanReturnService.registerReturn(loanId, returnedQuantity, borrowerEmail));

        assertEquals("A quantidade devolvida não pode ser maior que a quantidade emprestada.", exception.getMessage());
        verify(loanComponentRepository).findById(loanId);
        verify(userRepository).findOptionalByEmail(borrowerEmail);
    }
}
