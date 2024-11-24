package robotic.system.loanComponent.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanRequestServiceTest {

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private LoanComponentRepository loanComponentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private LoanRequestService loanRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestLoan_Success() {
        String componentName = "Test Component";
        int quantity = 5;
        Date expectedReturnDate = new Date();
        String borrowerEmail = "borrower@example.com";

        Component mockComponent = mock(Component.class);
        Users mockBorrower = mock(Users.class);
        LoanComponent mockLoan = mock(LoanComponent.class);

        when(componentRepository.findByName(componentName)).thenReturn(Optional.of(mockComponent));
        when(mockComponent.getQuantity()).thenReturn(10);
        when(loanComponentRepository.sumLoanedQuantitiesByComponentId(mockComponent.getId(), LoanStatus.RETURNED)).thenReturn(3);
        when(userRepository.findOptionalByEmail(borrowerEmail)).thenReturn(Optional.of(mockBorrower));
        when(loanComponentRepository.save(any(LoanComponent.class))).thenReturn(mockLoan);

        LoanComponent result = loanRequestService.requestLoan(componentName, quantity, expectedReturnDate, borrowerEmail);

        assertNotNull(result);
        verify(componentRepository).findByName(componentName);
        verify(loanComponentRepository).sumLoanedQuantitiesByComponentId(mockComponent.getId(), LoanStatus.RETURNED);
        verify(userRepository).findOptionalByEmail(borrowerEmail);
        verify(loanComponentRepository).save(any(LoanComponent.class));
    }

    @Test
    void testRequestLoan_ComponentNotFound() {
        String componentName = "Nonexistent Component";
        int quantity = 5;
        Date expectedReturnDate = new Date();
        String borrowerEmail = "borrower@example.com";

        when(componentRepository.findByName(componentName)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                loanRequestService.requestLoan(componentName, quantity, expectedReturnDate, borrowerEmail));

        assertEquals("Componente não encontrado: " + componentName, exception.getMessage());
        verify(componentRepository).findByName(componentName);
        verifyNoInteractions(loanComponentRepository, userRepository);
    }

    @Test
    void testRequestLoan_UserNotFound() {
        String componentName = "Test Component";
        int quantity = 5;
        Date expectedReturnDate = new Date();
        String borrowerEmail = "nonexistent@example.com";

        Component mockComponent = mock(Component.class);

        when(componentRepository.findByName(componentName)).thenReturn(Optional.of(mockComponent));
        when(mockComponent.getQuantity()).thenReturn(10);
        when(loanComponentRepository.sumLoanedQuantitiesByComponentId(mockComponent.getId(), LoanStatus.RETURNED)).thenReturn(3);
        when(userRepository.findOptionalByEmail(borrowerEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                loanRequestService.requestLoan(componentName, quantity, expectedReturnDate, borrowerEmail));

        assertEquals("Usuário não encontrado: " + borrowerEmail, exception.getMessage());
        verify(componentRepository).findByName(componentName);
        verify(userRepository).findOptionalByEmail(borrowerEmail);
    }

    @Test
    void testRequestLoan_InsufficientQuantity() {
        String componentName = "Test Component";
        int quantity = 10;
        Date expectedReturnDate = new Date();
        String borrowerEmail = "borrower@example.com";

        Component mockComponent = mock(Component.class);

        when(componentRepository.findByName(componentName)).thenReturn(Optional.of(mockComponent));
        when(mockComponent.getQuantity()).thenReturn(5);
        when(loanComponentRepository.sumLoanedQuantitiesByComponentId(mockComponent.getId(), LoanStatus.RETURNED)).thenReturn(0);

        Exception exception = assertThrows(RuntimeException.class, () ->
                loanRequestService.requestLoan(componentName, quantity, expectedReturnDate, borrowerEmail));

        assertEquals("No momento, apenas 5 componentes estão disponíveis para empréstimo.", exception.getMessage());
        verify(componentRepository).findByName(componentName);
        verify(loanComponentRepository).sumLoanedQuantitiesByComponentId(mockComponent.getId(), LoanStatus.RETURNED);
    }

    @Test
    void testCheckAndNotifyOverdueLoans() {
        LoanComponent mockLoan = mock(LoanComponent.class);

        when(loanComponentRepository.findOverdueLoans(any(Date.class), eq(LoanStatus.OVERDUE)))
                .thenReturn(List.of(mockLoan));

        loanRequestService.checkAndNotifyOverdueLoans();

        verify(loanComponentRepository).findOverdueLoans(any(Date.class), eq(LoanStatus.OVERDUE));
        verify(notificationService).notifyOverdueLoan(mockLoan);
    }
}
