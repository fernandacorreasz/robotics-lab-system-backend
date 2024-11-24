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
import robotic.system.notification.app.service.NotificationService;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private LoanComponentRepository loanComponentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Component component;

    @InjectMocks
    private LoanAuthorizationService loanAuthorizationService;

    @InjectMocks
    private LoanOverdueCheckService loanOverdueCheckService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthorizeLoan() {
        LoanComponent loan = mock(LoanComponent.class);
        Users authorizer = mock(Users.class);

        when(loanComponentRepository.findByLoanId(anyString())).thenReturn(Optional.of(loan));
        when(userRepository.findOptionalByEmail(anyString())).thenReturn(Optional.of(authorizer));
        when(loan.getComponent()).thenReturn(component);
        when(component.getQuantity()).thenReturn(10);
        when(loanComponentRepository.sumLoanedQuantitiesByComponentId(any(UUID.class), any(LoanStatus.class))).thenReturn(5);
        when(loanComponentRepository.save(any(LoanComponent.class))).thenReturn(loan);

        loanAuthorizationService.authorizeLoan("loanId", LoanStatus.APPROVED, 3, "authorizer@example.com");

        verify(loanComponentRepository).save(any(LoanComponent.class));
    }

    @Test
    void testCheckOverdueLoansForEmail() {
        LoanComponent loan = mock(LoanComponent.class);
        Users borrower = mock(Users.class);

        when(loanComponentRepository.findOverdueLoans(any(Date.class), any(LoanStatus.class)))
                .thenReturn(List.of(loan));
        when(loan.getBorrower()).thenReturn(borrower);
        when(borrower.getEmail()).thenReturn("borrower@example.com");

        loanOverdueCheckService.checkOverdueLoansForEmail("borrower@example.com");

        verify(notificationService).notifyOverdueLoan(any(LoanComponent.class));
    }




}
