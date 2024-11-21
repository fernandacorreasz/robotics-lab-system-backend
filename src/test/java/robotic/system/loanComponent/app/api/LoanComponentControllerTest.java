package robotic.system.loanComponent.app.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import robotic.system.loanComponent.app.service.LoanComponentService;
import robotic.system.loanComponent.app.service.LoanOverdueCheckService;
import robotic.system.loanComponent.app.service.LoanPickupService;
import robotic.system.loanComponent.domain.dto.ComponentWithLoanDetailsDTO;
import robotic.system.loanComponent.domain.dto.LoanComponentDTO;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoanComponentControllerTest {


    @Mock
    private LoanPickupService loanPickupService;


    @Mock
    private LoanOverdueCheckService loanOverdueCheckService;

    @Mock
    private LoanComponentService loanComponentService;

    @InjectMocks
    private LoanComponentController loanComponentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterPickup() {
        UUID loanId = UUID.randomUUID();
        LoanComponent mockLoan = new LoanComponent();

        when(loanPickupService.registerPickup(any(), anyString())).thenReturn(mockLoan);

        ResponseEntity<LoanComponent> response = loanComponentController.registerPickup(loanId, "test@example.com");

        assertEquals(200, response.getStatusCodeValue());
        verify(loanPickupService).registerPickup(any(), anyString());
    }

    @Test
    void testCheckOverdueLoans() {
        when(loanOverdueCheckService.checkOverdueLoansForEmail(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<LoanComponent>> response = loanComponentController.checkOverdueLoans("test@example.com");

        assertEquals(200, response.getStatusCodeValue());
        verify(loanOverdueCheckService).checkOverdueLoansForEmail(anyString());
    }

    @Test
    void testListAllLoans() {
        Page<LoanComponentDTO> mockPage = new PageImpl<>(Collections.emptyList());

        when(loanComponentService.listAllLoanComponents(any())).thenReturn(mockPage);

        ResponseEntity<Page<LoanComponentDTO>> response = loanComponentController.listAllLoans(0, 10);

        assertEquals(200, response.getStatusCodeValue());
        verify(loanComponentService).listAllLoanComponents(any());
    }


    @Test
    void testFilterLoanComponents() {
        List<FilterRequest> filters = Collections.emptyList();
        Page<LoanComponentDTO> mockPage = new PageImpl<>(Collections.emptyList());

        when(loanComponentService.filterLoanComponents(any(), any())).thenReturn(mockPage);

        ResponseEntity<Page<LoanComponentDTO>> response = loanComponentController.filterLoanComponents(filters, 0, 10);

        assertEquals(200, response.getStatusCodeValue());
        verify(loanComponentService).filterLoanComponents(any(), any());
    }

    @Test
    void testDeleteLoanComponents() {
        List<String> loanIds = List.of("loan1", "loan2");
        BulkDeleteService.BulkDeleteResult result = new BulkDeleteService.BulkDeleteResult();

        when(loanComponentService.deleteLoanComponentsByIds(any())).thenReturn(result);

        ResponseEntity<BulkDeleteService.BulkDeleteResult> response = loanComponentController.deleteLoanComponents(loanIds);

        assertEquals(200, response.getStatusCodeValue());
        verify(loanComponentService).deleteLoanComponentsByIds(any());
    }

    @Test
    void testGetComponentsWithLoanDetails() {
        when(loanComponentService.getComponentsWithLoanDetails()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ComponentWithLoanDetailsDTO>> response = loanComponentController.getComponentsWithLoanDetails();

        assertEquals(200, response.getStatusCodeValue());
        verify(loanComponentService).getComponentsWithLoanDetails();
    }
}
