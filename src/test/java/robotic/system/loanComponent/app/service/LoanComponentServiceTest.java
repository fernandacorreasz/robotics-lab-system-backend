package robotic.system.loanComponent.app.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.loanComponent.domain.dto.LoanComponentDTO;
import robotic.system.loanComponent.repository.LoanComponentRepository;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanComponentServiceTest {

    @Mock
    private LoanComponentRepository loanComponentRepository;

    @Mock
    private BulkDeleteService bulkDeleteService;

    @Mock
    private ComponentRepository componentRepository;

    @InjectMocks
    private LoanComponentService loanComponentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAllLoanComponentsIncludingNulls() {
        Pageable pageable = mock(Pageable.class);
        List<LoanComponentDTO> mockList = List.of(mock(LoanComponentDTO.class));
        Page<LoanComponentDTO> mockPage = new PageImpl<>(mockList);

        when(loanComponentRepository.findAllLoanComponentsWithAllDetails(pageable)).thenReturn(mockPage);

        Page<LoanComponentDTO> result = loanComponentService.listAllLoanComponentsIncludingNulls(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(loanComponentRepository).findAllLoanComponentsWithAllDetails(pageable);
    }

    @Test
    void testGetLoanById_Success() {
        UUID loanId = UUID.randomUUID();
        LoanComponentDTO mockLoan = mock(LoanComponentDTO.class);

        when(loanComponentRepository.findLoanById(loanId)).thenReturn(Optional.of(mockLoan));

        Optional<LoanComponentDTO> result = loanComponentService.getLoanById(loanId);

        assertTrue(result.isPresent());
        assertEquals(mockLoan, result.get());
        verify(loanComponentRepository).findLoanById(loanId);
    }

    @Test
    void testGetLoanById_NotFound() {
        UUID loanId = UUID.randomUUID();

        when(loanComponentRepository.findLoanById(loanId)).thenReturn(Optional.empty());

        Optional<LoanComponentDTO> result = loanComponentService.getLoanById(loanId);

        assertFalse(result.isPresent());
        verify(loanComponentRepository).findLoanById(loanId);
    }

    @Test
    void testFilterLoanComponents_NoValidFilters() {
        Pageable pageable = mock(Pageable.class);

        Page<LoanComponentDTO> result = loanComponentService.filterLoanComponents(Collections.emptyList(), pageable);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        verifyNoInteractions(loanComponentRepository);
    }


    @Test
    void testListLoansByBorrowerEmail() {
        Pageable pageable = mock(Pageable.class);
        String email = "borrower@example.com";
        List<LoanComponentDTO> mockList = List.of(mock(LoanComponentDTO.class));
        Page<LoanComponentDTO> mockPage = new PageImpl<>(mockList);

        when(loanComponentRepository.findAllLoanComponentsByBorrowerEmail(email, pageable)).thenReturn(mockPage);

        Page<LoanComponentDTO> result = loanComponentService.listLoansByBorrowerEmail(email, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(loanComponentRepository).findAllLoanComponentsByBorrowerEmail(email, pageable);
    }

    @Test
    void testListAllLoanComponents() {
        Pageable pageable = mock(Pageable.class);
        List<LoanComponentDTO> mockList = List.of(mock(LoanComponentDTO.class));
        Page<LoanComponentDTO> mockPage = new PageImpl<>(mockList);

        when(loanComponentRepository.findAllLoanComponentsWithDetails(pageable)).thenReturn(mockPage);

        Page<LoanComponentDTO> result = loanComponentService.listAllLoanComponents(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(loanComponentRepository).findAllLoanComponentsWithDetails(pageable);
    }

    @Test
    void testFilterLoanComponents_ValidFilters() {
        Pageable pageable = mock(Pageable.class);

        // Cria filtros válidos
        List<FilterRequest> filters = List.of(
                new FilterRequest("loanId", "equals", "test-loan-id"),
                new FilterRequest("borrower.email", "equals", "test@example.com")
        );

        // Configura mock para o repositório
        List<LoanComponentDTO> mockList = List.of(mock(LoanComponentDTO.class));
        Page<LoanComponentDTO> mockPage = new PageImpl<>(mockList);

        when(loanComponentRepository.findAllLoanComponentsWithFilters(
                eq("test-loan-id"), eq("test@example.com"), eq(null), eq(null), eq(pageable))
        ).thenReturn(mockPage);

        // Executa o método que está sendo testado
        Page<LoanComponentDTO> result = loanComponentService.filterLoanComponents(filters, pageable);

        // Validações
        assertNotNull(result, "O resultado do filtro não deve ser nulo.");
        assertEquals(1, result.getContent().size(), "O resultado deve conter exatamente 1 item.");

        // Verifica se o repositório foi chamado com os argumentos corretos
        verify(loanComponentRepository).findAllLoanComponentsWithFilters(
                eq("test-loan-id"), eq("test@example.com"), eq(null), eq(null), eq(pageable));
    }


}